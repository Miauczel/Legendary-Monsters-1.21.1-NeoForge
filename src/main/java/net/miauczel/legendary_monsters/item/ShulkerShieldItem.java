package net.miauczel.legendary_monsters.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class ShulkerShieldItem extends ShieldItem {
    public ShulkerShieldItem(){
        super(new Properties().durability(450).rarity(Rarity.EPIC));
    }
    public Ingredient getRepairIngredient() {
        return Ingredient.of(new ItemStack(Items.SHULKER_SHELL));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.legendary_monsters.shulker_shield1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.shulker_shield2"));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }
}
