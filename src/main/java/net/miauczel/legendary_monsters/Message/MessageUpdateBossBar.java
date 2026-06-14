package net.miauczel.legendary_monsters.Message;


import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record MessageUpdateBossBar(UUID uuid, int renderType) implements CustomPacketPayload {


    public static final Type<MessageUpdateBossBar> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "update_bossbar"));

    public static final StreamCodec<ByteBuf, MessageUpdateBossBar> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            MessageUpdateBossBar::uuid,
            ByteBufCodecs.INT,
            MessageUpdateBossBar::renderType,
            MessageUpdateBossBar::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
/*
public class MessageUpdateBossBar {

    private final UUID bossBar;
    private final int  renderType;

    public MessageUpdateBossBar(UUID bossBar, int renderType) {
        this.bossBar = bossBar;
        this.renderType = renderType;
    }


    public static MessageUpdateBossBar read(FriendlyByteBuf buf) {
        return new MessageUpdateBossBar(buf.readUUID(), buf.readInt());
    }

    public static void write(MessageUpdateBossBar message, FriendlyByteBuf buf) {
        buf.writeUUID(message.bossBar);
        buf.writeInt(message.renderType);
    }


    public static void handle(MessageUpdateBossBar message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        Player playerSided = context.get().getSender();
        if (context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            playerSided = LegendaryMonsters.PROXY.getClientSidePlayer();
        }
        if(message.renderType == -1){
            LegendaryMonsters.PROXY.removeBossBarRender(message.bossBar);
        }else{
            LegendaryMonsters.PROXY.setBossBarRender(message.bossBar, message.renderType);
        }
    }
}*/