package net.miauczel.legendary_monsters.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.miauczel.legendary_monsters.client.ModBlockEntityWithoutLevelRenderer;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.item.ModToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ShatteredGreatSwordItem extends SwordItem {

    public ShatteredGreatSwordItem(Properties pProperties) {
        super(ModToolTiers.SHATTERED_GREATSWORD, pProperties.rarity(Rarity.EPIC));
    }

    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");


    public static final ResourceLocation REACHID = ResourceLocation.withDefaultNamespace("reach");
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack st) {

        if (st.getEquipmentSlot() != EquipmentSlot.MAINHAND) return super.getDefaultAttributeModifiers(st);

        double damage = 11 * ModConfig.MOB_CONFIG.ShatteredGreatSwordDamageMultiplier.get();
        double speed = -3D * ModConfig.MOB_CONFIG.ShatteredGreatSwordSpeedMultiplier.get();
        double range = 1D * ModConfig.MOB_CONFIG.ShatteredGreatSwordRangeMultiplier.get();


        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(REACHID, range, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ModBlockEntityWithoutLevelRenderer.INSTANCE;
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.legendary_monsters.shattered_greatsword1").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
