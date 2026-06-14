package net.miauczel.legendary_monsters.Message;

import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SkeloraptorRoarKeyMessage(int entityID) implements CustomPacketPayload {


    public static final Type<SkeloraptorRoarKeyMessage> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "skeloraptor_roar"));

    public static final StreamCodec<ByteBuf, SkeloraptorRoarKeyMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SkeloraptorRoarKeyMessage::entityID,
            SkeloraptorRoarKeyMessage::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

/*
public class SkeloraptorRoarKeyMessage {
    public int equipmentSlot;
    public int playerId;
    public int type;

    public SkeloraptorRoarKeyMessage(int equipmentSlot, int playerId, int type) {
        this.equipmentSlot = equipmentSlot;
        this.playerId = playerId;
        this.type = type;
    }

    public SkeloraptorRoarKeyMessage() {}

    public static SkeloraptorRoarKeyMessage read(FriendlyByteBuf buf) {
        int equipmentSlot = buf.readInt();
        int playerId = buf.readInt();
        int type = buf.readInt();
        return new SkeloraptorRoarKeyMessage(equipmentSlot, playerId, type);
    }

    public static void write(SkeloraptorRoarKeyMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.equipmentSlot);
        buf.writeInt(message.playerId);
        buf.writeInt(message.type);
    }

    public static class Handler {
        public static void onMessage(SkeloraptorRoarKeyMessage message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            context.enqueueWork(() -> {
                ServerPlayer sender = context.getSender();
                if (sender != null) {
                    if (sender.getVehicle() instanceof SkeloraptorEntity dino) {
                        if (dino.getAttackState() ==0 && dino.roarCooldown <=0) {
                            dino.roarCooldown = dino.ROAR_COOLDOWN;
                                dino.setAttackState(1);


                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}*/
