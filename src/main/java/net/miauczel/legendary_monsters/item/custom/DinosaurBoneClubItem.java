package net.miauczel.legendary_monsters.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ShockwaveSpawner;
import net.miauczel.legendary_monsters.item.ModToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.UUID;

public class DinosaurBoneClubItem extends SwordItem {
    public DinosaurBoneClubItem(Properties properties) {
        super(ModToolTiers.DINOSAUR_BONE,properties.rarity(Rarity.EPIC).durability(1650));

    }
    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");


    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack st) {

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (st.getEquipmentSlot() != EquipmentSlot.MAINHAND)return super.getDefaultAttributeModifiers(st);

        double damage = 8 * ModConfig.MOB_CONFIG.DinosaurBoneClubDamageMultiplier.get();
        double speed = -3D * ModConfig.MOB_CONFIG.DinosaurBoneClubSpeedMultiplier.get();

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
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
                Vec3 entityPosition = player.position();
                CameraShakeEntity.cameraShake(player.level(), entityPosition, 15.0F, 0.1F, 0, 15);
                UUID playerUUID = player.getUUID();
                player.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 2F, 1.0F);

                if (!world.isClientSide && !player.getCooldowns().isOnCooldown(this)) {
                    int standingOnY = Mth.floor(player.getY());// Sprawdzamy, czy kod wykonuje się po stronie serwera



                    player.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 2F, 1.0F);
                            this.spawnshockwaves(blockPos.getX() , blockPos.getZ() , standingOnY, blockPos.getY() , 1, 0, player);


                        player.getCooldowns().addCooldown(this, 70);


                    }}

            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return InteractionResult.FAIL;
    }

    private void spawnshockwaves(double x, double z, double minY, double maxY, float rotation, int delay, Player player) {

        BlockPos blockpos = new BlockPos((int) x, (int) maxY, (int) z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = player.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(player.level(), blockpos1, Direction.UP)) {
                if (!player.level().isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = player.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(player.level(), blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            LivingEntity entity1 = (LivingEntity) player;
            player.level().addFreshEntity(new ShockwaveSpawner(player.level(), x, (double)blockpos.getY() + d0, z, rotation, delay, entity1, false));
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("item.legendary_monsters.dino_club1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.dino_club2"));
    }
}


