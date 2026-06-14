package net.miauczel.legendary_monsters.item;

import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties INFECTED_CHORUS_FRUIT = new FoodProperties.Builder().nutrition(5)
            .saturationModifier(0.2f).effect(() -> new MobEffectInstance((Holder<MobEffect>) ModEffects.CHORUSINFECTION, 160),100f).build();

    public static final FoodProperties CLOUD_BREAD = new FoodProperties.Builder().nutrition(5)
            .saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, MathUtils.toTicks(2)),100f).build();


}
