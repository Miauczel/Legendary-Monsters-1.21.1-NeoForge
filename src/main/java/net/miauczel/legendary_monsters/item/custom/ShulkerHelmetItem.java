package net.miauczel.legendary_monsters.item.custom;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.item.custom.customArmor.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = LegendaryMonsters.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ShulkerHelmetItem extends ArmorItem {
    private static final MobEffectInstance MOSSY_ARMOR_EFFECT =
            new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 1, false, false, true);



    public ShulkerHelmetItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.stacksTo(1));
    }



    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();
            if (!player.level().isClientSide()) {
                ItemStack headItemStack = player.getInventory().armor.get(EquipmentSlot.HEAD.getIndex());
                if (!headItemStack.isEmpty() && headItemStack.getItem() instanceof ShulkerHelmetItem) {
                    removeLevitationEffect(player);
                }
            }
        }


    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player && !event.getEntity().level().isClientSide()) {
            Player player = (Player) event.getEntity();
            ItemStack headItemStack = player.getInventory().armor.get(EquipmentSlot.HEAD.getIndex());
            if (!headItemStack.isEmpty() && headItemStack.getItem() instanceof ShulkerHelmetItem) {
                removeLevitationEffect(player);
            }
        }
    }

    private static void removeLevitationEffect(Player player) {
        if (player.hasEffect(MobEffects.LEVITATION)) {
            player.removeEffect(MobEffects.LEVITATION);
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
       tooltipComponents.add(Component.translatable("item.legendary_monsters.shulker_helmet1"));
    }
}
