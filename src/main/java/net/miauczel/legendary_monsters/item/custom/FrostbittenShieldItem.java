package net.miauczel.legendary_monsters.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class FrostbittenShieldItem extends ShieldItem {
    public FrostbittenShieldItem() {
        super(new Properties().durability(350).rarity(Rarity.EPIC));
    }


    public Ingredient getRepairIngredient() {
        return Ingredient.of(new ItemStack(Items.IRON_INGOT));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("item.legendary_monsters.frost_shield1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.frost_shield2"));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }
}
