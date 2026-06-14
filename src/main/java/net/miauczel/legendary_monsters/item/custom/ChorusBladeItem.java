package net.miauczel.legendary_monsters.item.custom;

import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.ModToolTiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Properties;

public class ChorusBladeItem extends SwordItem {
    public ChorusBladeItem(Properties properties) {
        super(ModToolTiers.VOID,properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        if(entity.isShiftKeyDown()) {
            entity.getCooldowns().addCooldown(this, 20);
            ChorusBladeRightClicked.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
        }else {
            entity.getCooldowns().addCooldown(this, 20);
      ChorusBladeRightClicked.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
            }
        return ar;
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.legendary_monsters.chorus_blade1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.chorus_blade2"));
    }
}
