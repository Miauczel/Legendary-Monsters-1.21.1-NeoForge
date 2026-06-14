package net.miauczel.legendary_monsters.item.custom;

import net.miauczel.legendary_monsters.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.UUID;


public class WitheredScytheItem extends Item {

    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public WitheredScytheItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {

        if (stack.getEquipmentSlot() != EquipmentSlot.MAINHAND) return super.getDefaultAttributeModifiers(stack);

        double damage = 9 * ModConfig.MOB_CONFIG.WitheredScytheDamageMultiplier.get();
        double speed = -2.8D * ModConfig.MOB_CONFIG.WitheredScytheSpeedMultiplier.get();

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public float dashBaseDamage() {
        return (float) (10 * ModConfig.MOB_CONFIG.WitheredScytheAbilityDamageMultiplier.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        pStack.hurtAndBreak(1, pAttacker, LivingEntity.getSlotForHand(pAttacker.getUsedItemHand()));
        return true;

    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if ((double) pState.getDestroySpeed(pLevel, pPos) != 0.0D) {
            pStack.hurtAndBreak(1, pEntityLiving, LivingEntity.getSlotForHand(pEntityLiving.getUsedItemHand()));
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);


        if (!player.getCooldowns().isOnCooldown(this)) {
            initialYRot = player.getYRot();
            initialXRot = player.getXRot();
            aTime = 20;

        }
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.legendary_monsters.withered_scythe1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.withered_scythe2"));
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

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pEntity instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() == this) {
                g = this.getUseDuration(pStack,player);
            }
        }


    }


    public int getEnchantmentValue() {
        return 1;
    }


    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(this)) {
                player.getCooldowns().addCooldown(this, 120);
            }


        }

        initialYRot = null;
        initialXRot = null;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    private Float initialYRot = null;
    private Float initialXRot = null;
    public boolean hasStartedOnGround = false;
    private int aTime = 30;

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof Player player && aTime > 0 && initialYRot != null && initialXRot != null) {
            player.setYRot(initialYRot);
            player.setXRot(initialXRot);
        }

        if (aTime > 0) {
            --aTime;
        }
        if (aTime == 2) {
            if (pLivingEntity.onGround()) {
                hasStartedOnGround = true;
            }

        }
        if (aTime <= 0) {
            if (pLivingEntity instanceof Player player) {
                if (!player.getCooldowns().isOnCooldown(this)) {

                    hasStartedOnGround = false;
                    player.getCooldowns().addCooldown(this, 75);
                }


            }


        }
        if (!(aTime == 0)) {
            if (pLivingEntity instanceof Player player) {

                int i = this.getUseDuration(pStack,player) - pRemainingUseDuration;
                int t = Mth.clamp(i, 1, 4);
                float f7 = pLivingEntity.getYRot();
                float f = pLivingEntity.getXRot();
                float f1 = -Mth.sin(f7 * 0.017453292F) * Mth.cos(f * 0.017453292F);
                float f2 = -Mth.sin(f * 0.017453292F);
                float f3 = Mth.cos(f7 * 0.017453292F) * Mth.cos(f * 0.017453292F);
                float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                float f5 = 0.4F * ((float) t / 6.0F);
                f1 *= f5 / f4;
                f3 *= f5 / f4;
                if (hasStartedOnGround) {
                    pLivingEntity.push((double) f1, 0.0, (double) f3);
                } else {
                    if (pLivingEntity.onGround()) {

                        pLivingEntity.push((double) f1, 0.0, (double) f3);
                    }
                }
                if (pLivingEntity.onGround()) {
                    float f6 = 1.1999999F;
                    pLivingEntity.move(MoverType.SELF, new Vec3(0.0, (double) 0, 0.0));
                }
                double dashRadius = 1.25;
                AABB areaOfEffect = pLivingEntity.getBoundingBox().inflate(dashRadius);
                List<LivingEntity> entities = pLevel.getEntitiesOfClass(LivingEntity.class, areaOfEffect);

                for (LivingEntity target : entities) {
                    if (target != pLivingEntity && target != null && !(target instanceof TamableAnimal animal && animal.getOwner() == animal.getOwner())) {

                        DamageSource damageSource = new DamageSource(player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), player);
                        target.hurt(damageSource, dashBaseDamage());
                        launch(player, target, false);
                        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1), player);
                    }
                }
            }
        }
    }

    private void launch(LivingEntity pl, LivingEntity entity, boolean huge) {
        double deltaX = entity.getX() - pl.getX();
        double deltaZ = entity.getZ() - pl.getZ();
        double distanceSquared = Math.max(deltaX * deltaX + deltaZ * deltaZ, 0.0001);
        float multiplier = huge ? 1.0F : 0.5F;
        entity.push(deltaX / distanceSquared * (double) multiplier, huge ? 0.5 : 0.2, deltaZ / distanceSquared * (double) multiplier);
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    private int g;

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility);
    }
}








