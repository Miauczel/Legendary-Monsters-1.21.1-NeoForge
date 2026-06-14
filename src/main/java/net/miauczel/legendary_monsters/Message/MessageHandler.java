package net.miauczel.legendary_monsters.Message;

import net.miauczel.legendary_monsters.item.custom.KeybindArmor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageHandler {
    public static void handleMessageArmorKey(MessageArmorKey messageArmorKey, IPayloadContext context){
        Player player = context.player();
        if (player != null) {
            EquipmentSlot equipmentSlot1 = EquipmentSlot.values()[Mth.clamp(messageArmorKey.equipmentSlot(), 0, EquipmentSlot.values().length - 1)];
            ItemStack stack = player.getItemBySlot(equipmentSlot1);
            Item patt1574$temp = stack.getItem();
            if (patt1574$temp instanceof KeybindArmor) {
                KeybindArmor armor = (KeybindArmor)patt1574$temp;
                armor.onKeyPacket(player, stack, messageArmorKey.Mtype());
            }
        }
    }
}
