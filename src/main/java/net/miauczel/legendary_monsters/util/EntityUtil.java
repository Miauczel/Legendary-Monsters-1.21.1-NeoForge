package net.miauczel.legendary_monsters.util;

import net.miauczel.legendary_monsters.config.ModConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.function.Predicate;

import static net.minecraft.world.entity.projectile.ProjectileUtil.getEntityHitResult;

public class EntityUtil {
    public static void applyPlayerDeltaMovement(LivingEntity entity) {
        if (entity instanceof ServerPlayer) {
            ((ServerPlayer) entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
        }
    }

    public static void applyServerTeleport(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel level) {
            level.addDuringTeleport(entity);
        }
    }


    public static final ResourceLocation HEALTH_MULT_UUID = ResourceLocation.withDefaultNamespace("hp");

    public static final ResourceLocation DAMAGE_MULT_UUID = ResourceLocation.withDefaultNamespace("dmg");

    public static void applyHealthMultiplier(LivingEntity livingEntity, double healthMultiplier) {
        if (livingEntity.level().isClientSide) return;
        AttributeInstance maxHp = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHp == null) return;
        if (livingEntity.getHealth() != livingEntity.getMaxHealth() || (livingEntity.getHealth() != livingEntity.getMaxHealth() && livingEntity.getHealth() != maxHp.getBaseValue()))
            return;
        if (maxHp.getModifier(HEALTH_MULT_UUID) != null) {
            maxHp.removeModifier(HEALTH_MULT_UUID);
        }
        double amount = healthMultiplier - 1.0;
        if (amount != 0.0) {
            AttributeModifier modifier = new AttributeModifier(
                    HEALTH_MULT_UUID,
                    amount,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
            maxHp.addPermanentModifier(modifier);
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }

        livingEntity.setHealth(Math.min(livingEntity.getHealth(), livingEntity.getMaxHealth()));
    }

    public static void cancelBuffs(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player)) return;

        ItemStack itemStack = player.getUseItem();
        if (itemStack.has(DataComponents.FOOD) && player.isUsingItem() && player.getUseItem().is(itemStack.getItem()) && ModConfig.MOB_CONFIG.BossesCancelEating.get()) {
            player.getCooldowns().addCooldown(itemStack.getItem(), 20);
            player.stopUsingItem();
        }
        MobEffectInstance mobEffectInstance = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
        if (player.hasEffect(MobEffects.DAMAGE_RESISTANCE) && mobEffectInstance != null && mobEffectInstance.getAmplifier() > 1) {
            player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, mobEffectInstance.getDuration(), 0));
        }

    }


    public static void applyStackingEffect(LivingEntity entity, Holder<MobEffect> effect, int bonusLevel, int maxLevel, int duration) {

        MobEffectInstance effectInstance = entity.getEffect(effect);

        if (entity.hasEffect(effect) && effectInstance != null) {

            int effectLevel = effectInstance.getAmplifier();
            if (effectLevel < maxLevel) {
                entity.addEffect(new MobEffectInstance(effect, duration, effectLevel + bonusLevel));
            }
        } else if (!(entity.hasEffect( effect) && effectInstance != null)) {
            entity.addEffect(new MobEffectInstance( effect, duration, 0));
        }
    }
}
