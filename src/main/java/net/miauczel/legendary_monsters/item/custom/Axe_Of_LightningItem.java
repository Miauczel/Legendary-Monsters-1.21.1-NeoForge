package net.miauczel.legendary_monsters.item.custom;


import com.google.common.collect.ImmutableMultimap;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ElectricityEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.UUID;

public class Axe_Of_LightningItem extends AxeItem {

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility)
               ;
    }
    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    public Axe_Of_LightningItem(Tier tier, Properties properties) {
        super(tier, properties.rarity(Rarity.EPIC).fireResistant());


    }


    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack st) {

        if (st.getEquipmentSlot() != EquipmentSlot.MAINHAND)return super.getDefaultAttributeModifiers(st);

        double damage = 12D * ModConfig.MOB_CONFIG.AxeOfLightningDamageMultiplier.get();
        double speed = -2.8D * ModConfig.MOB_CONFIG.AxeOfLightningSpeedMultiplier.get();

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        pStack.hurtAndBreak(1, pAttacker, LivingEntity.getSlotForHand(pAttacker.getUsedItemHand()));
        return true;

    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        pStack.hurtAndBreak(1, pEntityLiving, LivingEntity.getSlotForHand(pEntityLiving.getUsedItemHand()));
        return true;
    }

    @Override
    public void setDamage(ItemStack stack, int damage){
        super.setDamage(stack, -1);
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
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        BlockPos blockPos = context.getClickedPos();

        if (player != null) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (!player.getCooldowns().isOnCooldown(this)) {
                double theta = (player.getYHeadRot()) * (Math.PI / 180);

                theta += Math.PI / 2;
                double vecX = Math.cos(theta);
                double vecZ = Math.sin(theta);
                int numberOfSkulls = 5;
                float angleStep = 15.0f;
player.playSound(SoundEvents.WITHER_SHOOT,1,0.9f);
                for (int i = 0; i < numberOfSkulls; i++) {
                    float angle = player.getYHeadRot() + (i - (numberOfSkulls / 2)) * angleStep;

                    float rad = (float) Math.toRadians(angle);
                    double dx = -Math.sin(rad);
                    double dz = Math.cos(rad);
                    AttributeInstance attackDamageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
                    if (attackDamageAttribute != null) {
                        float damage = (float) attackDamageAttribute.getValue();


                        float y = Mth.floor(player.getY());
                        ElectricityEntity witherskull = new ElectricityEntity(player, dx, 0, dz, player.level(), damage, angle, 20);
                        double spawnX = player.getX() + vecX * 1;
                        double spawnY = y;
                        double spawnZ = player.getZ() + vecZ * 1;
                        witherskull.setPos(spawnX, spawnY, spawnZ);
                        player.level().addFreshEntity(witherskull);

                        player.getCooldowns().addCooldown(this, 60);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
        }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        int standingOnY = Mth.floor(player.getY()) - 1;
        double headY = player.getY() + 1.0D;
        float yawRadians = (float) (Math.toRadians(90 + player.getYRot()));
        boolean hasSucceeded = false;

player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER,3f,1f);
                for (int l = 0; l < 12; l++) {
                    double d2 = 1.25D * (double) (l + 1);
                    if (this.spawnFangs(player.getX() + (double) Mth.cos(yawRadians) * d2, headY, player.getZ() + (double) Mth.sin(yawRadians) * d2, standingOnY, yawRadians, l, world, player))
                        hasSucceeded = true;

                }


        ItemStack stack = player.getItemInHand(hand);
        if (hasSucceeded) {
            player.getCooldowns().addCooldown(this, 90);
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    private boolean spawnFangs(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, Player player) {
        BlockPos blockpos = BlockPos.containing(x, y, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = world.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
                if (!world.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while (blockpos.getY() >= lowestYCheck);

        if (flag) {
            world.addFreshEntity(new LightningBoltEntity(world, x, (double) blockpos.getY() + d0, z, yRot, warmupDelayTicks, (LivingEntity) player,30, (float) (8 * ModConfig.MOB_CONFIG.AxeOfLightningBoltDamageMultiplier.get())));
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.legendary_monsters.axe_of_lightning1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.axe_of_lightning2"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.axe_of_lightning3"));
    }
}
