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
    import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
    import net.neoforged.neoforge.event.tick.PlayerTickEvent;

    import java.util.List;

    @EventBusSubscriber(modid = LegendaryMonsters.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public class WitheredRibcageItem extends ArmorItem {


        private static final MobEffectInstance MOSSY_ARMOR_EFFECT =
                new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 1, false, false, true);

        public WitheredRibcageItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
            super(material, type, properties.stacksTo(1));
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
                        removePoisonEffect(player);
                    }
                    }
                }

        }

        @SubscribeEvent
        public static void onLivingDamage(LivingDamageEvent.Pre event) {
            if (event.getEntity() instanceof Player && !event.getEntity().level().isClientSide()) {
                Player player = (Player) event.getEntity();
                if (hasMossyChestplate(player)) {
                    removePoisonEffect(player);
                }
            }
        }

        private static boolean hasMossyChestplate(Player player) {
            ItemStack chestplateStack = player.getInventory().armor.get(2);

            return !chestplateStack.isEmpty() && chestplateStack.getItem() instanceof ModArmorItem &&
                    ((ModArmorItem) chestplateStack.getItem()).getMaterial() == ModArmorMaterials.WITHER;
        }

        private static void addMossyArmorEffect(Player player) {
            boolean hasPlayerEffect = player.hasEffect(MOSSY_ARMOR_EFFECT.getEffect());

            if (!hasPlayerEffect) {
                player.addEffect(new MobEffectInstance(MOSSY_ARMOR_EFFECT));
            }
        }

        private static void removePoisonEffect(Player player) {
            MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.WITHER);
            if (player.hasEffect(poisonEffect.getEffect())) {
                player.removeEffect(poisonEffect.getEffect());
            }
        }


        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

            tooltipComponents.add(Component.translatable("item.legendary_monsters.withered_ribcage1"));
           tooltipComponents.add(Component.translatable("item.legendary_monsters.withered_ribcage2"));
        }
    }
