package net.miauczel.legendary_monsters.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class CustomShieldItem extends ShieldItem {
    public CustomShieldItem(){
        super(new Properties().durability(2750).fireResistant().rarity(Rarity.EPIC));
    }
    public Ingredient getRepairIngredient() {
        return Ingredient.of(new ItemStack(Blocks.BONE_BLOCK));
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("item.legendary_monsters.dino_shield"));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }
}
