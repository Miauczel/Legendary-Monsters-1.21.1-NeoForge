package net.miauczel.legendary_monsters.Network;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.Message.*;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedBoss;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.custom.KeybindArmor;
import net.miauczel.legendary_monsters.sound.BossMusicPlayer;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.concurrent.CompletableFuture;

public class LMServerPayLoadHandler implements IPayloadHandler {



    public static void handleMusic(final PlayBossMusicMessage data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Entity e = Minecraft.getInstance().level.getEntity(data.entityID());
            if (!(e instanceof IAnimatedBoss boss)) return;

            if (data.play()) {
                BossMusicPlayer.playBossMusic(boss);
            } else {
                BossMusicPlayer.stopBossMusic(boss);
            }
        });
    }

    public static void handleSkeloraptorRoar(final SkeloraptorRoarKeyMessage data, final IPayloadContext context) {

        context.enqueueWork(() -> {
            ServerPlayer sender = (ServerPlayer) context.player();
            if (sender.getVehicle() instanceof SkeloraptorEntity dino) {
                if (dino.getAttackState() == 0 && dino.roarCooldown <= 0) {
                    dino.roarCooldown = dino.ROAR_COOLDOWN;
                    dino.setAttackState(1);


                }
            }
        });
    }
    public static void handleSkeloraptorTail(final SkeloraptorTailAttackMessage data, final IPayloadContext context) {

        context.enqueueWork(() -> {
            ServerPlayer sender = (ServerPlayer) context.player();
            if (sender.getVehicle() instanceof SkeloraptorEntity dino) {
                if (dino.getAttackState() == 0) {
                    switch (dino.getRandom().nextInt(2)) {
                        case 0 -> {
                            dino.setAttackState(3);
                        }
                        case 1 -> {
                            dino.setAttackState(4);
                        }

                    }

                }
            }
        });
    }

    public static void handleUpdateBossBar(final MessageUpdateBossBar data, final IPayloadContext context) {
        context.handle(data);

    }

    public static void handleAnnihilatorHelmetAbility(final AnnihilatorHelmetAbilityMessage data, final IPayloadContext context) {
        context.handle(data);
        context.enqueueWork(() -> {
            ServerPlayer sender = (ServerPlayer) context.player();
            if (sender != null) {
                BlockHitResult hitResult = PlayerLookAtHitResult(sender.level(), sender, ClipContext.Fluid.NONE);
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos blockPos = hitResult.getBlockPos();
                    if (MathUtils.distanceToVec(sender.position(), blockPos.getCenter()) < teleportRange) {
                        Vec3 pos = blockPos.getCenter();
                        double d1 = pos.x;
                        double d2 = pos.y + 1;
                        double d3 = pos.z;
                        teleport(d1, blockPos.getY() + 1, d3, sender);
                        // System.out.println("BLOCK XYZ: " + d1 + " " + d2 + " " + d3);
                        if (sender.level() instanceof ServerLevel level)
                            level.playSound(null, blockPos, SoundEvents.SHULKER_TELEPORT, SoundSource.PLAYERS, 1, 1);
                        sender.getCooldowns().addCooldown(ModItems.ANNIHILATOR_HELMET.get(), MathUtils.toTicks(7));
                    }
                }
            }
        });

    }

    public static int teleportRange = 15;

    public static BlockHitResult PlayerLookAtHitResult(Level pLevel, Player pPlayer, ClipContext.Fluid pFluidMode) {
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        Vec3 vec3 = pPlayer.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = teleportRange;
        Vec3 vec31 = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
    }

    public static boolean teleport(double x, double y, double z, ServerPlayer player) {
        if (player.level() instanceof ServerLevel level) {
            level.sendParticles(ModParticles.TELEPORT_EFFECT.get(), player.getX(), player.getY() + 3, player.getZ(), 1, 0, 0, 0, 0);
        }

        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

        while (blockpos$mutableblockpos.getY() > player.level().getMinBuildHeight() && !player.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = player.level().getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.blocksMotion();
        if (flag) {

            EntityTeleportEvent.EnderEntity event = EventHooks.onEnderTeleport(player, x, y, z);
            if (event.isCanceled()) return false;
            Vec3 vec3 = player.position();
            boolean flag2 = teleportBoolean(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true, player);
            if (flag2) {
                player.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));
                if (!player.isSilent()) {
                    CameraShakeEntity.cameraShake(player.level(), player.position(), 10, 0.1f, 5, 5);
                }
            }

            return flag2;

        } else {
            return false;
        }

    }

    public static boolean teleportBoolean(double x, double y, double z, boolean p_20988_, ServerPlayer player) {
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();
        double d3 = y;
        boolean flag = false;
        BlockPos blockpos = BlockPos.containing(x, y, z);
        Level level = player.level();
        if (level.hasChunkAt(blockpos)) {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > level.getMinBuildHeight()) {
                BlockPos blockpos1 = blockpos.below();
                BlockState blockstate = level.getBlockState(blockpos1);
                if (blockstate.blocksMotion()) {
                    flag1 = true;
                } else {
                    --d3;
                    blockpos = blockpos1;
                }
            }


            if (flag1) {
                player.teleportTo(x, d3, z);
                // EntityUtil.applyServerTeleport(this);
                if (level.noCollision(player) && !level.containsAnyLiquid(player.getBoundingBox())) {
                    flag = true;
                }
            }
        }

        if (!flag) {

            player.teleportTo(d0, d1, d2);
            // EntityUtil.applyServerTeleport(this);
            return false;
        } else {
            if (p_20988_) {
                level.broadcastEntityEvent(player, (byte) 46);
            }
            //player.getNavigation().stop();

            return true;
        }
    }

    @Override
    public void handle(CustomPacketPayload customPacketPayload, IPayloadContext iPayloadContext) {

    }
}
