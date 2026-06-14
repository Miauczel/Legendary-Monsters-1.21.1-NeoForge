package net.miauczel.legendary_monsters.Network;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.Message.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class LMClientPayLoadHandler implements IPayloadHandler {

    public static void handleMessageArmorKey(final MessageArmorKey data, final IPayloadContext context) {
        context.handle(data);
    }

    public static void handleMusic(final PlayBossMusicMessage data, final IPayloadContext context) {
       // context.handle(data);
    }
    public static void handleSkeloraptorRoar(final SkeloraptorRoarKeyMessage data, final IPayloadContext context) {
        context.handle(data);
    }
    public static void handleSkeloraptorTail(final SkeloraptorTailAttackMessage data, final IPayloadContext context) {
        context.handle(data);
    }
    public static void handleUpdateBossBar(final MessageUpdateBossBar data, final IPayloadContext context) {
       // context.handle(data);
        context.player();
        if (context.flow().getReceptionSide() == LogicalSide.CLIENT) {
            LegendaryMonsters.proxy.getClientSidePlayer();
        }
        if(data.renderType() == -1){
            LegendaryMonsters.  proxy.removeBossBarRender(data.renderType());
        }else{
            LegendaryMonsters.proxy.setBossBarRender(data.uuid(), data.renderType());
        }
    }
    public static void handleAnnihilatorHelmetAbility(final AnnihilatorHelmetAbilityMessage data, final IPayloadContext context) {
        context.handle(data);

    }

    @Override
    public void handle(CustomPacketPayload customPacketPayload, IPayloadContext iPayloadContext) {

    }
}
