package net.miauczel.legendary_monsters.Message;

import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.item.custom.KeybindArmor;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageArmorKey(
        int equipmentSlot,
        int entityID,
        int Mtype
) implements CustomPacketPayload {

    public static final Type<MessageArmorKey> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    LegendaryMonsters.MOD_ID,
                    "armor_key"
            ));

    public static final StreamCodec<ByteBuf, MessageArmorKey> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    MessageArmorKey::equipmentSlot,
                    ByteBufCodecs.INT,
                    MessageArmorKey::entityID,
                    ByteBufCodecs.INT,
                    MessageArmorKey::Mtype,
                    MessageArmorKey::new
            );

    public static void handleMessageArmorKey(
            MessageArmorKey data,
            IPayloadContext context
    ) {
        if (!(context.player() instanceof ServerPlayer player)) {
            LegendaryMonsters.LOGGER.error(
                    "[LM NET] armor_key received without ServerPlayer"
            );
            return;
        }

        LegendaryMonsters.LOGGER.info(
                "[LM NET] Received armor_key from {}, slot={}, type={}, entityID={}",
                player.getGameProfile().getName(),
                data.equipmentSlot(),
                data.Mtype(),
                data.entityID()
        );

        EquipmentSlot[] slots = EquipmentSlot.values();

        if (data.equipmentSlot() < 0 ||
                data.equipmentSlot() >= slots.length) {

            LegendaryMonsters.LOGGER.warn(
                    "[LM NET] Invalid equipment slot: {}",
                    data.equipmentSlot()
            );
            return;
        }

        EquipmentSlot slot = slots[data.equipmentSlot()];
        ItemStack stack = player.getItemBySlot(slot);

        LegendaryMonsters.LOGGER.info(
                "[LM NET] Resolved slot={}, item={}",
                slot,
                stack.getItem()
        );

        if (!(stack.getItem() instanceof KeybindArmor armor)) {
            LegendaryMonsters.LOGGER.warn(
                    "[LM NET] Item in {} is not KeybindArmor: {}",
                    slot,
                    stack.getItem()
            );
            return;
        }

        try {
            armor.onKeyPacket(player, stack, data.Mtype());

            LegendaryMonsters.LOGGER.info(
                    "[LM NET] onKeyPacket completed successfully"
            );
        } catch (Throwable throwable) {
            LegendaryMonsters.LOGGER.error(
                    "[LM NET] Error inside KeybindArmor#onKeyPacket",
                    throwable
            );
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}