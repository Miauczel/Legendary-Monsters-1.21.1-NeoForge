package net.miauczel.legendary_monsters.item;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.item.custom.customArmor.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = LegendaryMonsters.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModArmorItem extends ArmorItem {

    private static final MobEffectInstance MOSSY_ARMOR_EFFECT =
            new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 1, false, false, true);

    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {

            Player player = event.getEntity();
            if (!player.level().isClientSide()) {
                if (hasMossyChestplate(player)) {
                    addMossyArmorEffect(player);
                }
            }

    }

    private static boolean hasMossyChestplate(Player player) {
        ItemStack chestplateStack = player.getInventory().getArmor(2);

        if (!chestplateStack.isEmpty() && chestplateStack.getItem() instanceof ModArmorItem && chestplateStack.getItem() == ModItems.MOSSY_CHESTPLATE) {
            return true;
        }

        return false;
    }

    private static void addMossyArmorEffect(Player player) {
        boolean hasPlayerEffect = player.hasEffect(MOSSY_ARMOR_EFFECT.getEffect());

        if (!hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(MOSSY_ARMOR_EFFECT));
        }
    }
}
