package net.miauczel.legendary_monsters.Message;

import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SkeloraptorTailAttackMessage(int entityID) implements CustomPacketPayload {


    public static final Type<SkeloraptorTailAttackMessage> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "skeloraptor_tail"));

    public static final StreamCodec<ByteBuf, SkeloraptorTailAttackMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SkeloraptorTailAttackMessage::entityID,
            SkeloraptorTailAttackMessage::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
/*
public class SkeloraptorTailAttackMessage {
    public int equipmentSlot;
    public int playerId;
    public int type;

    public SkeloraptorTailAttackMessage(int equipmentSlot, int playerId, int type) {
        this.equipmentSlot = equipmentSlot;
        this.playerId = playerId;
        this.type = type;
    }

    public SkeloraptorTailAttackMessage() {}

    public static SkeloraptorTailAttackMessage read(FriendlyByteBuf buf) {
        int equipmentSlot = buf.readInt();
        int playerId = buf.readInt();
        int type = buf.readInt();
        return new SkeloraptorTailAttackMessage(equipmentSlot, playerId, type);
    }

    public static void write(SkeloraptorTailAttackMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.equipmentSlot);
        buf.writeInt(message.playerId);
        buf.writeInt(message.type);
    }

    public static class Handler {
        public static void onMessage(SkeloraptorTailAttackMessage message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            context.enqueueWork(() -> {
                ServerPlayer sender = context.getSender();
                if (sender != null) {
                    if (sender.getVehicle() instanceof SkeloraptorEntity dino) {
                        if (dino.getAttackState() ==0) {
                            switch (dino.getRandom().nextInt(2)){
                                case 0->{dino.setAttackState(3);}
                                case 1->{dino.setAttackState(4);}

                            }

                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}
*/