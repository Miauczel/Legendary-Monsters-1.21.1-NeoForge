package net.miauczel.legendary_monsters.effect;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.effect.custom.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;

public class ModEffects {


    public static final net.neoforged.neoforge.registries.DeferredRegister<MobEffect> MOB_EFFECTS
            = net.neoforged.neoforge.registries.DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, LegendaryMonsters.MOD_ID);
    public static final Holder<MobEffect> SOUL_FRACTURE = MOB_EFFECTS.register("soul_fracture",
            () -> new SoulEater(MobEffectCategory.HARMFUL, 542834).addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "health"), -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final Holder<MobEffect> CHORUSINFECTION = MOB_EFFECTS.register("chorus_infection",
            () -> new ChorusInfection(MobEffectCategory.HARMFUL, 3124687));

    public static final Holder<MobEffect> FREEZE = MOB_EFFECTS.register("freeze",
            () -> new Freeze(MobEffectCategory.HARMFUL, 3124687));

    public static final Holder<MobEffect> UNBREAKABLE = MOB_EFFECTS.register("unbreakable",
            () -> new Unbreakable(MobEffectCategory.BENEFICIAL, 0x11ff00));

    public static final Holder<MobEffect> BLEEDING = MOB_EFFECTS.register("bleeding",
            () -> new Bleeding(MobEffectCategory.HARMFUL, 11141120));


    public static final Holder<MobEffect> SOUL_RAGE = MOB_EFFECTS.register("soul_rage", () -> new Rage(MobEffectCategory.BENEFICIAL,11141120)
            .addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "armor"), (double)6F, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "attack_speed"), (double)0.5F, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.ARMOR_TOUGHNESS,ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "tough"), (double)1F, AttributeModifier.Operation.ADD_VALUE));
    public static final Holder<MobEffect> BROKEN_ARMOR = MOB_EFFECTS.register("broken_armor", () -> new BrokenArmor(MobEffectCategory.HARMFUL,0)
            .addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "armor"), -2F, AttributeModifier.Operation.ADD_VALUE));

    public static final Holder<MobEffect> STUN = MOB_EFFECTS.register("stun", () -> new Stun(MobEffectCategory.HARMFUL,0)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "move_speed"), -1, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "attack_speed"), -1, AttributeModifier.Operation.ADD_VALUE));

    public static final Holder<MobEffect> GRAVITY_PULL = MOB_EFFECTS.register("gravity_pull", () -> new FastFalling(MobEffectCategory.HARMFUL,0)
            .addAttributeModifier(Attributes.GRAVITY,
                    ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "gravity_pull_gravity"), 0.05, AttributeModifier.Operation.ADD_VALUE));
    public static final Holder<MobEffect> PHARAONS_CURSE = MOB_EFFECTS.register("curse_of_desert", () -> new FastFalling(MobEffectCategory.HARMFUL,0));
    public static final Holder<MobEffect> FEAR = MOB_EFFECTS.register("fear", () -> new FastFalling(MobEffectCategory.HARMFUL,11141120));

    public static final Holder<MobEffect> ANNIHILATION = MOB_EFFECTS.register("annihilation", () -> new Annihilation(MobEffectCategory.HARMFUL,0)
            .addAttributeModifier(Attributes.ATTACK_SPEED,
                    ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "attack_speed"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));//-0.125



    //public static final Holder<MobEffect> MOVEMENT_SPEED = register(1, "speed", (new MobEffect(MobEffectCategory.BENEFICIAL, 3402751)).addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}

