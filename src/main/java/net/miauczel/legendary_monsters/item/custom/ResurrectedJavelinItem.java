package net.miauczel.legendary_monsters.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.miauczel.legendary_monsters.client.ModBlockEntityWithoutLevelRenderer;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ThrownResurrectedJavelin;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ResurrectedJavelinItem extends Item implements ProjectileItem{


    public ResurrectedJavelinItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pProperties.durability(1250));
        resetUseTime();
    }

    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public static final ResourceLocation REACHID = ResourceLocation.withDefaultNamespace("reach");
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack st) {

        if (st.getEquipmentSlot() != EquipmentSlot.MAINHAND) return super.getDefaultAttributeModifiers(st);

        double damage = 9 * ModConfig.MOB_CONFIG.ResurrectedJavelinDamageMultiplier.get();
        double speed = -2.7D * ModConfig.MOB_CONFIG.ResurrectedJavelinSpeedMultiplier.get();
        double range = 1.35D * ModConfig.MOB_CONFIG.ResurrectedJavelinRangeMultiplier.get();


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

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public int useDuration = 0;

    public int getDurationOfUse() {
        return useDuration;
    }

    private static boolean isTooDamagedToUse(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i >= 10) {
                float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, player);
                if ((!(f > 0.0F) || player.isInWaterOrRain()) && !isTooDamagedToUse(stack)) {
                    Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND).orElse(SoundEvents.TRIDENT_THROW);
                    if (!level.isClientSide) {
                        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
                        if (f == 0.0F) {
                            ThrownResurrectedJavelin throwntrident = new ThrownResurrectedJavelin(level, player, stack);
                            throwntrident.setPos(player.getX(),player.getY() + 1.5f,player.getZ());
                            throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                            if (player.hasInfiniteMaterials()) {
                                throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            level.addFreshEntity(throwntrident);
                            level.playSound((Player)null, throwntrident, (SoundEvent)holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (!player.hasInfiniteMaterials()) {
                                player.getInventory().removeItem(stack);
                            }
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (f > 0.0F) {
                        float f7 = player.getYRot();
                        float f1 = player.getXRot();
                        float f2 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f1 * ((float)Math.PI / 180F));
                        float f3 = -Mth.sin(f1 * ((float)Math.PI / 180F));
                        float f4 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f1 * ((float)Math.PI / 180F));
                        float f5 = Mth.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
                        f2 *= f / f5;
                        f3 *= f / f5;
                        f4 *= f / f5;
                        player.push((double)f2, (double)f3, (double)f4);
                        player.startAutoSpinAttack(20, 8.0F, stack);
                        if (player.onGround()) {
                            float f6 = 1.1999999F;
                            player.move(MoverType.SELF, new Vec3((double)0.0F, (double)1.1999999F, (double)0.0F));
                        }

                        level.playSound((Player)null, player, (SoundEvent)holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }

    }

    int defaultUseTime = 0;

    public void resetUseTime() {
        useDuration = 0;
        defaultUseTime = 0;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        Player player = (Player) pEntity;

        if (!pIsSelected && getDurationOfUse() > 0 && pStack == player.getItemInHand(InteractionHand.MAIN_HAND)) resetUseTime();

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        defaultUseTime++;
        useDuration = this.getUseDuration(itemstack,player) - defaultUseTime;
        return InteractionResultHolder.consume(itemstack);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, LivingEntity.getSlotForHand(pAttacker.getUsedItemHand()));
        return true;
    }

    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if ((double) pState.getDestroySpeed(pLevel, pPos) != (double) 0.0F) {
            pStack.hurtAndBreak(2, pEntityLiving, LivingEntity.getSlotForHand(pEntityLiving.getUsedItemHand()));
        }
        return true;
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.RIPTIDE)
                || enchantment.is(Enchantments.MENDING)
                || enchantment.is(Enchantments.LOYALTY)
                || enchantment.is(Enchantments.UNBREAKING)
                || enchantment.is(Enchantments.IMPALING)
                || enchantment.is(Enchantments.CHANNELING);
    }

    @Override
    public ItemEnchantments getAllEnchantments(ItemStack stack, HolderLookup.RegistryLookup<Enchantment> lookup) {
        return super.getAllEnchantments(stack, lookup);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment);
    }

    public int getEnchantmentValue() {
        return 1;
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownResurrectedJavelin throwntrident = new ThrownResurrectedJavelin(ModEntities.RESURRECTED_JAVELIN.get(), level);
        Vec3 vec3 = new Vec3(pos.x(),pos.y(),pos.z());
        throwntrident.setPos(vec3);

        throwntrident.pickup = AbstractArrow.Pickup.ALLOWED;
        return throwntrident;
    }

}
