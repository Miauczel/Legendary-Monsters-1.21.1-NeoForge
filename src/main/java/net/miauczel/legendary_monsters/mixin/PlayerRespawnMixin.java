package net.miauczel.legendary_monsters.mixin;

import net.miauczel.legendary_monsters.block.ModBlocks;
import net.miauczel.legendary_monsters.block.custom.EnderAnchorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayer.class)
public abstract class PlayerRespawnMixin {

    @Inject(
            method = "findRespawnPositionAndUseSpawnBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void legendary_monsters$enderAnchorRespawn(
            boolean alive,
            DimensionTransition.PostDimensionTransition postDimensionTransition,
            CallbackInfoReturnable<DimensionTransition> cir
    ) {
        ServerPlayer player = (ServerPlayer) (Object) this;

        BlockPos respawnPos = player.getRespawnPosition();

        if (respawnPos == null) {
            return;
        }

        if (!Level.END.equals(player.getRespawnDimension())) {
            return;
        }

        MinecraftServer server = player.getServer();

        if (server == null) {
            return;
        }

        ServerLevel respawnLevel =
                server.getLevel(player.getRespawnDimension());

        if (respawnLevel == null) {
            return;
        }

        BlockState state = respawnLevel.getBlockState(respawnPos);

        if (!state.is(ModBlocks.ENDER_ANCHOR.get())) {
            return;
        }

        int charges = state.getValue(EnderAnchorBlock.CHARGES);

        if (charges <= 0) {
            return;
        }

        Optional<Vec3> standPosition =
                EnderAnchorBlock.findStandUpPosition(
                        EntityType.PLAYER,
                        respawnLevel,
                        respawnPos
                );

        if (standPosition.isEmpty()) {
            return;
        }

        if (!alive) {
            respawnLevel.setBlock(
                    respawnPos,
                    state.setValue(EnderAnchorBlock.CHARGES, charges - 1),
                    3
            );
        }

        cir.setReturnValue(new DimensionTransition(
                respawnLevel,
                standPosition.get(),
                Vec3.ZERO,
                player.getRespawnAngle(),
                0.0F,
                false,
                postDimensionTransition
        ));
    }
}