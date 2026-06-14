package net.miauczel.legendary_monsters.item.custom;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.PoisonousShockwaveSpawner;
import net.miauczel.legendary_monsters.item.ModArmorItem;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.custom.customArmor.ModArmorMaterials;
import net.miauczel.legendary_monsters.Message.MessageArmorKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = LegendaryMonsters.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class MossyChestplateItem extends ArmorItem implements KeybindArmor {


    private static final MobEffectInstance MOSSY_ARMOR_EFFECT =
            new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 1, false, false, true);


    public MossyChestplateItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.stacksTo(1));
    }

    private int g;

    public void onKeyPacket(Player player, ItemStack itemStack, int Type) {
        int standingOnY = Mth.floor(player.getY());
        if (player != null && !player.getCooldowns().isOnCooldown(this)) {
            this.spawnShockwaves(player.getX(), player.getZ(), standingOnY, player.getY(), 0, 0, player);


            player.getCooldowns().addCooldown((Item) ModItems.MOSSY_CHESTPLATE.get(), 240);
        }

    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean held) {
        super.inventoryTick(stack, level, entity, i, held);
        if (entity instanceof Player living) {
            if (living.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.MOSSY_CHESTPLATE.get() && level.isClientSide && LegendaryMonsters.proxy.getClientSidePlayer() == entity && LegendaryMonsters.proxy.isKeyDown(4)) {
                LegendaryMonsters.sendMSGToServer(new MessageArmorKey(EquipmentSlot.CHEST.ordinal(), living.getId(), 4));
                this.onKeyPacket(living, stack, 4);
            }
        }
    }

    private void spawnShockwaves(double x, double z, double minY, double maxY, float rotation, int delay, Player player) {
        BlockPos blockpos = new BlockPos((int) x, (int) maxY, (int) z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = player.level().getBlockState(blockpos1);

            if (blockstate.isFaceSturdy(player.level(), blockpos1, Direction.UP)) {
                if (!player.level().isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = player.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(player.level(), blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            LivingEntity entity1 = (LivingEntity) player;
            player.level().addFreshEntity(new PoisonousShockwaveSpawner(player.level(), x, (double) blockpos.getY() + d0, z, rotation, delay, entity1, false));
        }

    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();
            if (!player.level().isClientSide()) {
                ItemStack chestplateItemStack = player.getInventory().armor.get(EquipmentSlot.CHEST.getIndex());
                if (!chestplateItemStack.isEmpty() && chestplateItemStack.getItem() instanceof MossyChestplateItem) {
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
        ItemStack chestplateStack = player.getInventory().armor.get(2); // 2 represents the chestplate slot

        return !chestplateStack.isEmpty() && chestplateStack.getItem() instanceof ModArmorItem &&
                ((ModArmorItem) chestplateStack.getItem()).getMaterial() == ModArmorMaterials.MOSSY;
    }

    private static void addMossyArmorEffect(Player player) {
        boolean hasPlayerEffect = player.hasEffect(MOSSY_ARMOR_EFFECT.getEffect());

        if (!hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(MOSSY_ARMOR_EFFECT));
        }
    }

    private static void removePoisonEffect(Player player) {
        MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.POISON);
        if (player.hasEffect(poisonEffect.getEffect())) {
            player.removeEffect(poisonEffect.getEffect());
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);

        list.add(Component.translatable("item.legendary_monsters.mossy_chestplate1"));
        list.add(Component.translatable("item.legendary_monsters.mossy_chestplate2"));
    }
}
