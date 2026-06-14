package net.miauczel.legendary_monsters.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.FireBreath;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public class FieryJawItem extends AxeItem{

    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public FieryJawItem(Properties p_40524_) {
        super(Tiers.DIAMOND, p_40524_.durability(750).rarity(Rarity.EPIC));
    }


    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack st) {

        if (st.getEquipmentSlot() != EquipmentSlot.MAINHAND)return super.getDefaultAttributeModifiers(st);

        double damage = 5 * ModConfig.MOB_CONFIG.FieryJawDamageMultiplier.get();
        double speed = -3D * ModConfig.MOB_CONFIG.FieryJawSpeedMultiplier.get();


        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        Vec3 mouthPos = new Vec3(0, 1.75f, 0);
        entity.getCooldowns().addCooldown(this, 200);
        FireBreath breath = new FireBreath(ModEntities.FIRE_B.get(), entity.level(), (float) (7.5f * ModConfig.MOB_CONFIG.FieryJawAbilityDamageMultiplier.get())
                , entity);
        breath.absMoveTo(mouthPos.x, mouthPos.y, mouthPos.z, entity.yHeadRot, entity.getXRot());
        entity.level().addFreshEntity(breath);
        FieryJawRightClicked.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
        return ar;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("item.legendary_monsters.fiery_jaw1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.fiery_jaw2"));
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.SHARPNESS)
                || enchantment.is(Enchantments.SMITE)
                || enchantment.is(Enchantments.BANE_OF_ARTHROPODS)
                || enchantment.is(Enchantments.KNOCKBACK)
                || enchantment.is(Enchantments.FIRE_ASPECT)
                || enchantment.is(Enchantments.LOOTING)
                || enchantment.is(Enchantments.SWEEPING_EDGE)
                || enchantment.is(Enchantments.EFFICIENCY)
                || enchantment.is(Enchantments.SILK_TOUCH)
                || enchantment.is(Enchantments.FORTUNE)
                || enchantment.is(Enchantments.UNBREAKING)
                || enchantment.is(Enchantments.MENDING);
    }

}



