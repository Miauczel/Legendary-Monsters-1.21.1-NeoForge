package net.miauczel.legendary_monsters.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnnihilatorSmithingTemplate extends Item {
    public AnnihilatorSmithingTemplate(Properties pProperties) {
        super(pProperties);
    }

    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, pTooltipComponents, tooltipFlag);

        pTooltipComponents.add(Component.translatable("item.legendary_monsters.smithing_template.annihilator_upgrade1").withStyle(TITLE_FORMAT));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("item.legendary_monsters.smithing_template.annihilator_upgrade2").withStyle(TITLE_FORMAT));
        pTooltipComponents.add(Component.translatable("item.legendary_monsters.smithing_template.annihilator_upgrade3").withStyle(DESCRIPTION_FORMAT));
        pTooltipComponents.add(Component.translatable("item.legendary_monsters.smithing_template.annihilator_upgrade4").withStyle(TITLE_FORMAT));
        pTooltipComponents.add(Component.translatable("item.legendary_monsters.smithing_template.annihilator_upgrade5").withStyle(DESCRIPTION_FORMAT));
    }
}
