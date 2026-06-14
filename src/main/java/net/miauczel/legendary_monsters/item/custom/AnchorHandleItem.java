package net.miauczel.legendary_monsters.item.custom;

import com.google.common.collect.ImmutableMultimap;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.UUID;

public class AnchorHandleItem extends SwordItem {

    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public AnchorHandleItem(Properties properties) {
        super(Tiers.IRON, properties);
    }

    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {

        if (stack.getEquipmentSlot() != EquipmentSlot.MAINHAND) return super.getDefaultAttributeModifiers(stack);

        double damage = 5 * ModConfig.MOB_CONFIG.AnchorHandleDamageMultiplier.get();
        double speed = -3D * ModConfig.MOB_CONFIG.AnchorHandleSpeedMultiplier.get();

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }



    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.isPrimaryItemFor(stack, enchantment)
                || enchantment.is(Enchantments.MENDING);
    }
}



