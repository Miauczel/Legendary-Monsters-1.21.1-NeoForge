    package net.miauczel.legendary_monsters.item.custom;

    import net.miauczel.legendary_monsters.LegendaryMonsters;
    import net.miauczel.legendary_monsters.effect.ModEffects;
    import net.miauczel.legendary_monsters.item.ModArmorItem;
    import net.miauczel.legendary_monsters.item.ModItems;
    import net.miauczel.legendary_monsters.item.custom.customArmor.ModArmorMaterials;
    import net.minecraft.core.Holder;
    import net.minecraft.network.chat.Component;
    import net.minecraft.world.effect.MobEffect;
    import net.minecraft.world.effect.MobEffectInstance;
    import net.minecraft.world.effect.MobEffects;
    import net.minecraft.world.entity.EquipmentSlot;
    import net.minecraft.world.entity.player.Player;
    import net.minecraft.world.item.ArmorItem;
    import net.minecraft.world.item.ArmorMaterial;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.item.TooltipFlag;
    import net.minecraft.world.level.Level;
    import net.neoforged.bus.api.IEventBus;
    import net.neoforged.bus.api.SubscribeEvent;
    import net.neoforged.fml.common.EventBusSubscriber;
    import net.neoforged.neoforge.common.NeoForge;
    import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
    import net.neoforged.neoforge.event.tick.PlayerTickEvent;

    import java.util.List;

    public class ChorusHelmetItem extends ArmorItem {


        private static final MobEffectInstance MOSSY_ARMOR_EFFECT =
                new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 1, false, false, true);

        public ChorusHelmetItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
            super(ModArmorMaterials.CHORUS, type, properties.stacksTo(1));
        }


        private static boolean hasMossyChestplate(Player player) {
            ItemStack helmetStack = player.getInventory().armor.get(0); // 2 represents the chestplate slot

            return !helmetStack.isEmpty() && helmetStack.getItem() instanceof ModArmorItem && helmetStack.getItem() == ModItems.CHORUS_MASK;
        }

        private static void addMossyArmorEffect(Player player) {
            boolean hasPlayerEffect = player.hasEffect(MOSSY_ARMOR_EFFECT.getEffect());

            if (!hasPlayerEffect) {
                player.addEffect(new MobEffectInstance(MOSSY_ARMOR_EFFECT));
            }
        }

        private static void removeLevitationEffect(Player player) {
            MobEffectInstance levitationEffect = new MobEffectInstance((Holder<MobEffect>) ModEffects.CHORUSINFECTION);
            if (player.hasEffect(levitationEffect.getEffect())) {
                player.removeEffect(levitationEffect.getEffect());
            }
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            tooltipComponents.add(Component.translatable("item.legendary_monsters.chorus_helmet1"));
            tooltipComponents.add(Component.translatable("item.legendary_monsters.chorus_helmet2"));
        }
    }
