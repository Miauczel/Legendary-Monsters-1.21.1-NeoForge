package net.miauczel.legendary_monsters.item.custom;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.UUID;

public class SomberKeyItem extends SwordItem {



    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public SomberKeyItem(Properties properties) {
        super(Tiers.GOLD, properties.rarity(Rarity.EPIC).durability(350));
    }


    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack st) {

        if (st.getEquipmentSlot() != EquipmentSlot.MAINHAND)return super.getDefaultAttributeModifiers(st);

        double damage = 6.5 * ModConfig.MOB_CONFIG.SomberKeyDamageMultiplier.get();
        double speed = -2.5D * ModConfig.MOB_CONFIG.SomberKeySpeedMultiplier.get();


        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

}
