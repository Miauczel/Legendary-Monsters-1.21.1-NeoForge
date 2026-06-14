    package net.miauczel.legendary_monsters.item.custom;

    import net.miauczel.legendary_monsters.LegendaryMonsters;
    import net.minecraft.core.Holder;
    import net.minecraft.network.chat.Component;
    import net.minecraft.world.item.ArmorItem;
    import net.minecraft.world.item.ArmorMaterial;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.item.TooltipFlag;
    import net.minecraft.world.level.Level;
    import net.neoforged.fml.common.EventBusSubscriber;

    import java.util.List;

    public class AtmosphericBootsItem extends ArmorItem {


        public AtmosphericBootsItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
            super(material, type, properties.stacksTo(1));
        }

        @Override
        public void setDamage(ItemStack stack, int damage) {
                super.setDamage(stack, 0);
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

            tooltipComponents.add(Component.translatable("item.legendary_monsters.atm_boots1"));
        }
    }
