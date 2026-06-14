    package net.miauczel.legendary_monsters.item.custom;

    import net.miauczel.legendary_monsters.LegendaryMonsters;
    import net.miauczel.legendary_monsters.item.ModArmorItem;
    import net.miauczel.legendary_monsters.item.custom.customArmor.ModArmorMaterials;
    import net.minecraft.core.Holder;
    import net.minecraft.network.chat.Component;
    import net.minecraft.world.effect.MobEffectInstance;
    import net.minecraft.world.effect.MobEffects;
    import net.minecraft.world.entity.EquipmentSlot;
    import net.minecraft.world.entity.player.Player;
    import net.minecraft.world.item.ArmorItem;
    import net.minecraft.world.item.ArmorMaterial;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.item.TooltipFlag;
    import net.minecraft.world.level.Level;
    import net.neoforged.bus.api.SubscribeEvent;
    import net.neoforged.fml.common.EventBusSubscriber;
    import net.neoforged.neoforge.common.NeoForge;
    import net.neoforged.neoforge.event.tick.PlayerTickEvent;

    import java.util.List;

    @EventBusSubscriber(modid = LegendaryMonsters.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public class DinosaurBoneHelmet extends ArmorItem {


        private static final MobEffectInstance MOSSY_ARMOR_EFFECT =
                new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 1, false, false, true);


        public DinosaurBoneHelmet(Holder<ArmorMaterial> material, Type type, Properties properties) {
            super(material, type, properties.stacksTo(1));
            NeoForge.EVENT_BUS.register(this);
        }

        private static boolean hasMossyChestplate(Player player) {
            ItemStack chestplateStack = player.getInventory().armor.get(2);

            return !chestplateStack.isEmpty() && chestplateStack.getItem() instanceof ModArmorItem &&
                    ((ModArmorItem) chestplateStack.getItem()).getMaterial() == ModArmorMaterials.DINOSAUR_BONE;
        }
        @SubscribeEvent
        @SuppressWarnings("unused")
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
                Player player = event.getEntity();
                if (!player.level().isClientSide()) {
                    ItemStack chestplateItemStack = player.getInventory().armor.get(EquipmentSlot.CHEST.getIndex());
                    if (!chestplateItemStack.isEmpty() && chestplateItemStack.getItem() instanceof WitheredRibcageItem) {
                        if (hasMossyChestplate(player)) {
                            addMossyArmorEffect(player);
                        } else {
                            // removePoisonEffect(player);
                        }
                    }

            }
        }
        private static final MobEffectInstance BONE_EFFECT =
                new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 1, false, false, true);
        private static void addMossyArmorEffect(Player player) {
            boolean hasPlayerEffect = player.hasEffect(BONE_EFFECT.getEffect());

            if (!hasPlayerEffect) {
                player.addEffect(new MobEffectInstance(BONE_EFFECT));
            }
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            tooltipComponents.add(Component.literal("\u00A76SPECIAL ABILITY: \u00A76\u00A7l[HEAD PROTECTION] \u00A76 25% chance of ignoring damage from arrows or"));
            tooltipComponents.add(Component.literal("\u00A76mob projectiles"));
        }
    }
