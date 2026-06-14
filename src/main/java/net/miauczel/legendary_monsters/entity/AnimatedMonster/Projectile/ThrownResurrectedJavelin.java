package net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile;

import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.custom.ResurrectedJavelinItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class ThrownResurrectedJavelin extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY;
    private static final EntityDataAccessor<Boolean> ID_FOIL;
    private boolean dealtDamage;
    public int clientSideReturnTridentTickCount;

    public ThrownResurrectedJavelin(EntityType<? extends ThrownResurrectedJavelin> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownResurrectedJavelin(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        super(ModEntities.RESURRECTED_JAVELIN.get(), shooter, level, pickupItemStack, null);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
    }

    public ThrownResurrectedJavelin(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(ModEntities.RESURRECTED_JAVELIN.get(), x, y, z, level, pickupItemStack, pickupItemStack);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_LOYALTY, (byte)0);
        builder.define(ID_FOIL, false);
    }
    public ParticleOptions getTrailParticle(){
        return ParticleTypes.CLOUD;
    }
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        if (this.level().isClientSide) {
            this.level().addParticle(this.getTrailParticle(), getX(), getY() + 0.5D, getZ(), 0.0D, 0.0D, 0.0D);
            if (tickCount % 3 == 0) {
                float yaw = (float) Math.toRadians(-getYRot() + 180);
                double theta = (this.getYRot()) * (Math.PI / 180);

                theta += Math.PI / 2;
                double vecX = Math.cos(theta);
                double vecZ = Math.sin(theta);
                Vec3 vec3 = getDeltaMovement();
                double spawnX = this.getX() + vec3.x + vecX * 0.25;
                double spawnZ = this.getZ() + vec3.z + vecZ * 0.25f;
                if (!inGround) this.level().addParticle(new Circle.RingData(this.horizontalCollision ? 0 : yaw, horizontalCollision ? 90 : 0, 30,
                        1f, 1, 1f, 1f, 10, false, Circle.EnumRingBehavior.GROW_THEN_SHRINK), spawnX, getY(), spawnZ, 0, 0, 0);
            }
        }

        Entity entity = this.getOwner();
        int i = (Byte)this.entityData.get(ID_LOYALTY);
        if (i > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double)i, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05 * (double)i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));
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
        return entity != null && entity.isAlive() ? !(entity instanceof ServerPlayer) || !entity.isSpectator() : false;
    }

    public boolean isFoil() {
        return (Boolean)this.entityData.get(ID_FOIL);
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float f = 8.0F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, (Entity)(entity1 == null ? this : entity1));
        Level var7 = this.level();
        if (var7 instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            var7 = this.level();
            if (var7 instanceof ServerLevel) {
                ServerLevel serverlevel1 = (ServerLevel)var7;
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    protected void hitBlockEnchantmentEffects(ServerLevel level, BlockHitResult hitResult, ItemStack stack) {
        Vec3 vec3 = hitResult.getBlockPos().clampLocationWithin(hitResult.getLocation());
        Entity var6 = this.getOwner();
        LivingEntity var10002;
        if (var6 instanceof LivingEntity livingentity) {
            var10002 = livingentity;
        } else {
            var10002 = null;
        }

        EnchantmentHelper.onHitBlock(level, stack, var10002, this, (EquipmentSlot)null, vec3, level.getBlockState(hitResult.getBlockPos()), (p_348680_) -> this.kill());
    }

    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.RESURRECTED_JAVELIN.get());
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public void playerTouch(Player entity) {
        if (this.ownedBy(entity) || this.getOwner() == null) {
            super.playerTouch(entity);
        }

    }

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
            var10000 = (byte)Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127);
        } else {
            var10000 = 0;
        }

        return var10000;
    }

    public void tickDespawn() {
        int i = (Byte)this.entityData.get(ID_LOYALTY);
        if (this.pickup != Pickup.ALLOWED || i <= 0) {
            super.tickDespawn();
        }

    }

    protected float getWaterInertia() {
        return 0.99F;
    }

    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    static {
        ID_LOYALTY = SynchedEntityData.defineId(ThrownResurrectedJavelin.class, EntityDataSerializers.BYTE);
        ID_FOIL = SynchedEntityData.defineId(ThrownResurrectedJavelin.class, EntityDataSerializers.BOOLEAN);
    }
}
