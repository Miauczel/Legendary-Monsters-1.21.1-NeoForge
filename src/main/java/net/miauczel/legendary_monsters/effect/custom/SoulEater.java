package net.miauczel.legendary_monsters.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class SoulEater extends MobEffect {
    public SoulEater(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {
            if (pLivingEntity.hasEffect(MobEffects.ABSORPTION)) {
                pLivingEntity.removeEffect(MobEffects.ABSORPTION);
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
        return true;
    }


}

