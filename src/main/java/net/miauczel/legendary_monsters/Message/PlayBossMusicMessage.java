package net.miauczel.legendary_monsters.Message;

import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedBoss;
import net.miauczel.legendary_monsters.sound.BossMusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayBossMusicMessage(int entityID, boolean play) implements CustomPacketPayload {


    public static final Type<PlayBossMusicMessage> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "boss_music"));

    public static final StreamCodec<ByteBuf, PlayBossMusicMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            PlayBossMusicMessage::entityID,
            ByteBufCodecs.BOOL,
            PlayBossMusicMessage::play,
            PlayBossMusicMessage::new
    );
    public static void handleMusic(final PlayBossMusicMessage data, final IPayloadContext context) {
        context.handle(data);
        context.enqueueWork(() -> {
            assert Minecraft.getInstance().level != null;
            Entity e = Minecraft.getInstance().level.getEntity(data.entityID());
            if (!(e instanceof IAnimatedBoss boss)) return;

            if (data.play()) {
                BossMusicPlayer.playBossMusic(boss);
            } else {
                BossMusicPlayer.stopBossMusic(boss);
            }
        });
    }
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
/*
public class PlayBossMusicMessage {

    private final int entityID;
    private final boolean play;
    public PlayBossMusicMessage(int bossBar, boolean canPlay) {
        this.entityID = bossBar;
        this.play =canPlay;
    }
    public static PlayBossMusicMessage read(FriendlyByteBuf buf) {
        return new PlayBossMusicMessage(buf.readInt(), buf.readBoolean());
    }

    public static void write(PlayBossMusicMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.entityID);
        buf.writeBoolean(message.play);
    }

    public static class Handler {
        public static boolean onMessage(PlayBossMusicMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                Entity e = Minecraft.getInstance().level.getEntity(msg.entityID);
                if (!(e instanceof IAnimatedBoss boss)) return;


                if (msg.play) {
                        BossMusicPlayer.playBossMusic(boss);
                } else {
                    BossMusicPlayer.stopBossMusic(boss);
                }
            });
            ctx.get().setPacketHandled(true);
            return true;
        }
    }

}

 */
