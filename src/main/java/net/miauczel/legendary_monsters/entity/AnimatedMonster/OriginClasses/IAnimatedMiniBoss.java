package net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses;

import net.miauczel.legendary_monsters.block.ModBlocks;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class IAnimatedMiniBoss extends IAnimatedMob {

    protected IAnimatedMiniBoss(EntityType<? extends IAnimatedMiniBoss> entityType, Level level) {
        super(entityType, level);
        lastTargetX = getX();
        lastTargetY = getY();
        lastTargetZ = getZ();
        setPersistenceRequired();
    }

    public Vec3 lastTargetPos() {
        return new Vec3(lastTargetX, lastTargetY, lastTargetZ);
    }

    private static final EntityDataAccessor<BlockPos> SPAWN_POS =
            SynchedEntityData.defineId(IAnimatedMiniBoss.class, EntityDataSerializers.BLOCK_POS);


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SPAWN_POS, BlockPos.ZERO);
    }

    public double lastTargetX, lastTargetZ, lastTargetY;

    public void saveTargetPos(double x, double y, double z) {
        if (targetIsNotNull()) {
            lastTargetX = x;

            lastTargetY = y;

            lastTargetZ = z;
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        float x = pCompound.getInt("posX");
        float y = pCompound.getInt("posY");
        float z = pCompound.getInt("posZ");
        BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);
        setSpawnBlockPos(blockPos);
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("posX", getSpawnBlockPos().getX());
        pCompound.putInt("posY", getSpawnBlockPos().getY());
        pCompound.putInt("posZ", getSpawnBlockPos().getZ());
        super.addAdditionalSaveData(pCompound);
    }

    /// DATA VALUE
    public BlockPos getSpawnBlockPos() {
        return entityData.get(SPAWN_POS);
    }

    public void setSpawnBlockPos(BlockPos blockPos) {
        entityData.set(SPAWN_POS, blockPos);
    }

    /// COOLDOWNS AND TICKING

    public final int INACTIVE_TICKS = 260;
    public final int RETURN_TO_SPAWN_TICKS = 100;
    public int inActiveTicks = INACTIVE_TICKS;
    public int return_to_spawn_ticks = RETURN_TO_SPAWN_TICKS;


    public final int REDUCED_DAMAGE_TICKS = 100;
    public int reducedDamageTicks = REDUCED_DAMAGE_TICKS;
    public final int TICKS_WITHOUT_TARGET = 60;
    public int ticksWithoutTarget = TICKS_WITHOUT_TARGET;

    public void regainHealthWithoutTarget(float health, float speed) {

        if (ModConfig.MOB_CONFIG.AllowBossNatureHeal.get()) {
            if (!level().isClientSide) {
                if (!targetIsNotNull() && ticksWithoutTarget > 0 && getAttackState() == 0) {
                    --ticksWithoutTarget;

                }
                if (ticksWithoutTarget <= 0 && !targetIsNotNull()) {
                    if (tickCount % speed == 0) {
                        this.heal(health);
                    }
                }
                if (targetIsNotNull()) {
                    ticksWithoutTarget = TICKS_WITHOUT_TARGET;
                }
            }
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        setSpawnBlockPos(this.blockPosition());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);

    }

    @Override
    public void tick() {
        if (targetIsNotNull()) inActiveTicks = INACTIVE_TICKS;
        if (inActiveTicks > 0) inActiveTicks--;
        if (return_to_spawn_ticks > 0 && inActiveTicks <= 0 && getSpawnBlockPos().distToCenterSqr(position()) >= 225)
            return_to_spawn_ticks--;
        if (reducedDamageTicks > 0) reducedDamageTicks--;
        regainHealthWithoutTarget(20, 15);
        if (this.getTarget() == null && !level().isClientSide && inActiveTicks > 0) inActiveTicks--;
        if (inActiveTicks <= 0 && return_to_spawn_ticks <= 0 && !level().isClientSide && getSpawnBlockPos().distToCenterSqr(position()) >= 225) {
            if (ModConfig.MOB_CONFIG.canBossesTeleportBackToSpawn.get()) moveToBlockPos(getSpawnBlockPos());
            return_to_spawn_ticks = RETURN_TO_SPAWN_TICKS;
        }
        super.tick();
    }

    public void moveToBlockPos(BlockPos blockPos) {
        this.moveTo(blockPos, getYRot(), getXRot());
    }

    public double damageCap() {
        return ModConfig.MOB_CONFIG.MiniBossDamageCap.get();
    }

    public float damageReduction() {
        return 1;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance instance) {
        return false;
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (entityData.get(SPAWN_POS) == BlockPos.ZERO) setSpawnBlockPos(this.blockPosition());

        setPersistenceRequired();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    public int hurtCount = 0;

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (isTargetCheesing(-4, 4)) return false;
        if (reducedDamageTicks <= 0) {
            reducedDamageTicks = REDUCED_DAMAGE_TICKS;
        }
        if (pSource.is(DamageTypes.FALL)) return false;
        if ((pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !pSource.is(DamageTypes.MAGIC)) && reducedDamageTicks > 0) {
            pAmount *= damageReduction();
        }
        if (pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.hurt(pSource, pAmount);
        } else {
            pAmount = (float) Math.min(damageCap(), pAmount);
        }
        boolean hurt1 = super.hurt(pSource, pAmount);
        if (hurt1) {
            if (hurtCount >= 2) {
                hurtCount = 0;
                if (!this.level().isClientSide) {
                    if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        breakCheeseBlocks();
                    }
                }
            }
            hurtCount++;
        }
        return hurt1;
    }

    protected void breakCheeseBlocks() {
        if (!this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        boolean flag = false;
        float inflate = 1f;
        AABB aabb = this.getBoundingBox().inflate(inflate, inflate, inflate);
        Iterator<BlockPos> var3 = BlockPos.betweenClosed(Mth.floor(aabb.minX),
                Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX),
                Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)).iterator();
        while (true) {
            while (true) {
                BlockPos blockpos;
                BlockState blockstate;
                do {

                    do {
                        do {
                            if (!var3.hasNext()) {
                                return;
                            }

                            blockpos = var3.next();
                            blockstate = this.level().getBlockState(blockpos);
                        } while (blockstate.isAir());
                    } while (!blockstate.canEntityDestroy(this.level(), blockpos, this));

                } while (!EventHooks.onEntityDestroyBlock(this, blockpos, blockstate));

                {
                    if (!blockstate.is(ModBlocks.INDESTRUCTIBLE_BLOCK.get()))
                        flag = this.level().destroyBlock(blockpos, false, this) || flag;
                }
            }
        }
    }
}
