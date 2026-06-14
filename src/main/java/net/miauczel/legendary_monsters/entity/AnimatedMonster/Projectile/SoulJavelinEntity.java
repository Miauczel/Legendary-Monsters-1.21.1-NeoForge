package net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile;

import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.BeheadedKnight.BeheadedKnightEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.tag.ModEntityTags;
import net.miauczel.legendary_monsters.util.EntityUtil;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SoulJavelinEntity extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(SoulJavelinEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(SoulJavelinEntity.class, EntityDataSerializers.BOOLEAN);
    private ItemStack tridentItem = new ItemStack(Items.TRIDENT);
    private boolean dealtDamage;
    public int clientSideReturnTridentTickCount;

    public SoulJavelinEntity(EntityType<? extends SoulJavelinEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SoulJavelinEntity(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(ModEntities.SOUL_JAVELIN.get(), pLevel);
        this.tridentItem = pStack.copy();
        setOwner(pShooter);
        // this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(pStack));
        this.entityData.set(ID_FOIL, pStack.hasFoil());
    }
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.TRIDENT);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_LOYALTY, (byte) 0);
        builder.define(ID_FOIL, false);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.getType().is(ModEntityTags.POSSESSED_ARMOR_TEAM) || super.isAlliedTo(pEntity);
    }

    public void tick() {

        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        for (int i = 0; i < 0.5; ++i) {
            if (level().isClientSide) {
                this.level().addParticle(ModParticles.GHOSTLY_SOUL.get(), this.getRandomX(1D), this.getRandomY(), this.getRandomZ(1D), 0D, 0.025D, 0D);

            }

        }
        Entity entity = this.getOwner();
        int i = this.entityData.get(ID_LOYALTY);
        if (i > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double) i, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double) i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnTridentTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnTridentTickCount;
            }
        }

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }



    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    /**
     * Gets the EntityHitResult representing the entity hit
     */
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();

        float f = entity instanceof LivingEntity livingEntity ? 8.0F + MathUtils.entityBasedHpDamage(livingEntity, 3) : 8.0F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = ModDamageTypes.causeGhostlyDamage(getOwner(), (LivingEntity) getOwner());
        Level var7 = this.level();
        if (var7 instanceof ServerLevel serverlevel) {
           // f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity instanceof LivingEntity livingEntity1) {
                if (getOwner() != null && getOwner() instanceof LivingEntity livingEntity2) livingEntity2.heal(8);
                EntityUtil.applyStackingEffect(livingEntity1, ModEffects.SOUL_FRACTURE, 1, 5, MathUtils.toTicks(10));
            }
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            var7 = this.level();
            if (var7 instanceof ServerLevel) {
                ServerLevel serverlevel1 = (ServerLevel) var7;
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        float f9 = (this.random.nextFloat() - 0.5F) * 8.0F;
        float f10 = (this.random.nextFloat() - 0.5F) * 4.0F;
        float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;

        float f8 = (this.random.nextFloat() - 0.75F) * 5.0F;
        float f6 = (this.random.nextFloat() - 0.75F) * 3.0F;
        float f7 = (this.random.nextFloat() - 0.75F) * 5.0F;


        this.level().addParticle(ModParticles.SOUL_EXPLOSION.get(),
                this.getX() + (double) f9, this.getY() + (double) f10, this.getZ() + (double) f2, 0.0D, 0.0D, 0.0D);
        this.level().addParticle(ModParticles.SOUL_EXPLOSION.get(),
                this.getX() + (double) f8, this.getY() + (double) f6, this.getZ() + (double) f7, 0.0D, 0.0D, 0.0D);

        this.level().addParticle(ModParticles.GHOSTLY_SOUL.get(),
                this.getX() + (double) f9, this.getY() + 2.0D + (double) f10, this.getZ() + (double) f2, 0.0D, 0.5D, 0.0D);
        this.level().addParticle(ModParticles.GHOSTLY_SOUL.get(),
                this.getX() + (double) f8, this.getY() + 2.0D + (double) f6, this.getZ() + (double) f7, 0.0D, 0.5D, 0.0D);
        if (!level().isClientSide) {
            if ((pResult instanceof EntityHitResult result && result.getEntity() instanceof BeheadedKnightEntity)) {

            } else {
                if (pResult instanceof BlockHitResult) {
                    discard();
                }
            }
        }
    }


    protected boolean tryPickup(Player pPlayer) {
        return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(Player pEntity) {
        if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.dealtDamage = compound.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("DealtDamage", this.dealtDamage);
    }

    private byte getLoyaltyFromItem(ItemStack stack) {
        Level var3 = this.level();
        byte var10000;
        if (var3 instanceof ServerLevel serverlevel) {
            var10000 = (byte) Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127);
        } else {
            var10000 = 0;
        }

        return var10000;
    }

    public void tickDespawn() {
        int i = this.entityData.get(ID_LOYALTY);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
            super.tickDespawn();
        }

    }

    protected float getWaterInertia() {
        return 0.99F;
    }

    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }
}