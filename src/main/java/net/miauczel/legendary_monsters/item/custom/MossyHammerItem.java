package net.miauczel.legendary_monsters.item.custom;

import com.google.common.collect.ImmutableMultimap;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.item.ModToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public class MossyHammerItem extends PickaxeItem {

    public MossyHammerItem(Properties properties) {
        super(ModToolTiers.MOSSY_HAMMER, properties.durability(900).rarity(Rarity.EPIC));
    }

    public static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack st) {

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (st.getEquipmentSlot() != EquipmentSlot.MAINHAND) return super.getDefaultAttributeModifiers(st);

        double damage = 8 * ModConfig.MOB_CONFIG.MossyHammerDamageMultiplier.get();
        double speed = -3.2D * ModConfig.MOB_CONFIG.MossyHammerSpeedMultiplier.get();

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        if (player != null) {
            Level level = player.level();
            BlockPos blockPos = pContext.getClickedPos();
            Vec3 vec3 = blockPos.getCenter();

            if (!player.getCooldowns().isOnCooldown(this)) {
                for (LivingEntity entity : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3.25f))) {
                    if (entity instanceof Player player2 && player2 == player) continue;
                    if (entity instanceof Player player1 && player1.getAbilities().invulnerable) continue;
                    if (entity instanceof TamableAnimal animal && animal.isTame() && animal.getOwner() == player)
                        continue;

                    boolean hurt = entity.hurt(entity.damageSources().playerAttack(player), (float) (10 * ModConfig.MOB_CONFIG.MossyHammerDamageMultiplier.get()));
                    if (hurt) {
                        entity.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0));
                        entity.push(0, 0.25f, 0);
                    }

                }
                player.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1, 1);
                CameraShakeEntity.cameraShake(player.level(), player.position(), 5, 0.15f, 5, 5);
                player.getCooldowns().addCooldown(this, 60);

//Particles

                BlockState blockState = level.getBlockState(blockPos);
                if (level.isClientSide) {
                    for (int i = 0; i < 360; i++) {

                        if (i % 2 == 0) {
                            double d1 = player.getRandom().nextGaussian() * 1f;
                            double d2 = player.getRandom().nextGaussian() * 1f;
                            double d3 = player.getRandom().nextGaussian() * 1f;
                            //        double angle = (Math.toRadians(-player.getYRot() + 180)) +i;
//double x = 2f * Mth.sin((float) (Math.PI * angle));
//double z = 2f * Mth.cos((float) (Math.PI * angle));
//double x1 = 2f * Mth.sqrt(i);
//double z1 = 2f * Mth.sqrt(i);
                            level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3.x, blockPos.getY() + 1, vec3.z, d1, d2, d3);
                        }
                    }
                    level.addParticle(new Circle.RingData((float) 0, (float) (Math.PI / 2), 30, 0.25f, 0.85f, 0.25f, 1, 50, false, Circle.EnumRingBehavior.GROW)
                            , player.getX(), player.getY(), player.getZ(), 0, 0, 0);
                }

            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("item.legendary_monsters.mossy_hammer1"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.mossy_hammer2"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.mossy_hammer3"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.mossy_hammer4"));
        tooltipComponents.add(Component.translatable("item.legendary_monsters.mossy_hammer5"));
    }
}
