package net.miauczel.legendary_monsters.item;


import net.miauczel.legendary_monsters.LegendaryMonsters;

import net.miauczel.legendary_monsters.tag.ModTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
public class ModToolTiers {

    public static final Tier DINOSAUR_BONE = new SimpleTier(
            ModTags.Blocks.NEEDS_DINOSAUR_BONE_TOOL,
            1900,
            0.5f,
             2f,
            25,
            () -> Ingredient.of(ModItems.DINOSAUR_BONE.get())
    );
    public static final Tier VOID = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            1100,
            0.5f,
            2f,
            25,
            () -> Ingredient.of(ModItems.DINOSAUR_BONE.get())
    );
    public static final Tier BRONZITE = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            450,
            6.5f,
            2f,
            14,
            () -> Ingredient.of(ModItems.DINOSAUR_BONE.get())
    );
    public static final Tier Lightning = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            3000,
            10f,
            2f,
            25,
            () -> Ingredient.of(ModItems.DINOSAUR_BONE.get())
    );
    public static final Tier ENDIRITIUM = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            2450,
            9f,
            3.5f,
            15,
            () -> Ingredient.of(ModItems.ENDERITIUM_INGOT.get())
    );
    public static final Tier TESSERACT = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            2100,
            9f,
            6f,
            15,
            () -> Ingredient.of(ModItems.DINOSAUR_BONE.get())
    );
    public static final Tier SHATTERED_GREATSWORD = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            1500,
            9f,
            6f,
            15,
            () -> Ingredient.of(Items.IRON_INGOT)
    );
    public static final Tier THE_GREAT_FROST = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            700,
            9f,
            6f,
            15,
            () -> Ingredient.of(Items.DIAMOND)
    );
    public static final Tier MOSSY_HAMMER = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            900,
            9f,
            6f,
            15,
            () -> Ingredient.of(Items.IRON_INGOT)
    );
    public static final Tier MONSTROUS_ANCHOR = new SimpleTier(
            ModTags.Blocks.NEEDS_VOID_TOOL,
            1450,
            9f,
            6f,
            15,
            () -> Ingredient.of(Items.IRON_INGOT)
    );


}