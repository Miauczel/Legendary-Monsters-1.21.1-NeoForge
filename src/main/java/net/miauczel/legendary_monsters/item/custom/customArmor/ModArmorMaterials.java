package net.miauczel.legendary_monsters.item.custom.customArmor;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class ModArmorMaterials extends ArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, LegendaryMonsters.MOD_ID);

    public static final int MOSSY_DURABILITY = 26;
    public static final int BLASTPROOF_DURABILITY = 26;
    public static final int BURNT_DURABILITY = 26;
    public static final int SHULKER_DURABILITY = 26;
    public static final int WITHER_DURABILITY = 26;
    public static final int CLOUDY_DURABILITY = 2;
    public static final int CHORUS_DURABILITY = 22;
    public static final int DINOSAUR_BONE_DURABILITY = 26;
    public static final int ANNIHILATOR_DURABILITY = 40;

    public static final Holder<ArmorMaterial> MOSSY = register(
            "mossy",
            defense(5, 8, 5, 4),
            25,
            sound(SoundEvents.IRON_GOLEM_REPAIR),
            () -> Ingredient.of(Items.IRON_INGOT),
            2.0f,
            0.5f
    );

    public static final Holder<ArmorMaterial> BLASTPROOF = register(
            "bomber",
            defense(3, 7, 5, 4),
            25,
            sound(SoundEvents.ARMOR_EQUIP_DIAMOND),
            () -> Ingredient.of(Items.IRON_INGOT),
            2.0f,
            0.1f
    );

    public static final Holder<ArmorMaterial> BURNT = register(
            "fiery",
            defense(5, 7, 5, 4),
            25,
            sound(SoundEvents.ARMOR_EQUIP_LEATHER),
            () -> Ingredient.of(ModItems.LAVA_EATERS_SKIN.get()),
            2.0f,
            0.0f
    );

    public static final Holder<ArmorMaterial> SHULKER = register(
            "shulker",
            defense(3, 7, 5, 4),
            25,
            sound(SoundEvents.ARMOR_EQUIP_LEATHER),
            () -> Ingredient.of(Items.SHULKER_SHELL),
            2.0f,
            0.0f
    );

    public static final Holder<ArmorMaterial> WITHER = register(
            "wither",
            defense(5, 7, 5, 4),
            25,
            sound(SoundEvents.BONE_BLOCK_BREAK),
            () -> Ingredient.of(Blocks.BONE_BLOCK),
            2.0f,
            0.1f
    );

    public static final Holder<ArmorMaterial> CLOUDY = register(
            "cloudy",
            defense(6, 7, 5, 4),
            25,
            sound(SoundEvents.ARMOR_EQUIP_LEATHER),
            () -> Ingredient.of(ModItems.CLOUD_ROD.get()),
            4.0f,
            0.0f
    );

    public static final Holder<ArmorMaterial> CHORUS = register(
            "chorus",
            defense(3, 7, 5, 4),
            25,
            sound(SoundEvents.ARMOR_EQUIP_LEATHER),
            () -> Ingredient.of(Items.CHORUS_FRUIT),
            1.0f,
            0.0f
    );

    public static final Holder<ArmorMaterial> DINOSAUR_BONE = register(
            "dinosaur_bone",
            defense(3, 7, 5, 4),
            25,
            sound(SoundEvents.BONE_BLOCK_BREAK),
            () -> Ingredient.of(Blocks.BONE_BLOCK),
            2.0f,
            0.2f
    );

    public static final Holder<ArmorMaterial> ANNIHILATOR = register(
            "annihilator",
            defense(6, 12, 8, 5),
            35,
            sound(SoundEvents.ARMOR_EQUIP_NETHERITE),
            () -> Ingredient.of(ModItems.ENDERITIUM_INGOT.get()),
            4.0f,
            0.2f
    );

    private static Holder<ArmorMaterial> register(
            String name,
            Map<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            Supplier<Ingredient> repairIngredient,
            float toughness,
            float knockbackResistance
    ) {
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                defense,
                enchantmentValue,
                equipSound,
                repairIngredient,
                List.of(new ArmorMaterial.Layer(
                        ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, name)
                )),
                toughness,
                knockbackResistance
        ));
    }

    private static Map<ArmorItem.Type, Integer> defense(
            int helmet,
            int chestplate,
            int leggings,
            int boots
    ) {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.HELMET, helmet);
            map.put(ArmorItem.Type.CHESTPLATE, chestplate);
            map.put(ArmorItem.Type.LEGGINGS, leggings);
            map.put(ArmorItem.Type.BOOTS, boots);
            map.put(ArmorItem.Type.BODY, chestplate);
        });
    }

    private static Holder<SoundEvent> sound(Holder<SoundEvent> sound) {
        return sound;
    }

    private static Holder<SoundEvent> sound(SoundEvent sound) {
        return BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound);
    }

    private ModArmorMaterials() {
    }
}