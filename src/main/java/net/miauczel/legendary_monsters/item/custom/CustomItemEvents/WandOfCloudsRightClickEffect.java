package net.miauczel.legendary_monsters.item.custom.CustomItemEvents;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import javax.annotation.Nullable;
@EventBusSubscriber
public class WandOfCloudsRightClickEffect {
    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != event.getEntity().getUsedItemHand())
            return;
        execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getTarget(), event.getEntity());
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null)
            return;

        double xRadius = 0;
        double loop = 0;
        double zRadius = 0;
        double particleAmount = 0;
        if (sourceentity instanceof Player dplayer) {
            ItemStack mainHandItem = dplayer.getMainHandItem();
            if (!mainHandItem.isEmpty() && mainHandItem.getItem() == ModItems.WAND_OF_CLOUDS.get() && !dplayer.getCooldowns().isOnCooldown(mainHandItem.getItem())) {
        if ((sourceentity instanceof Player p_14734 ? p_14734.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.WAND_OF_CLOUDS.get()) {
            loop = 0;
            particleAmount = 128;
            xRadius = 2;
            zRadius = 2;
            while (loop < particleAmount) {
                world.addParticle(ParticleTypes.ENCHANT, (entity.getX() + 0.5 + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRadius), y, (entity.getZ() + 0.5 + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRadius), 0, 0.05, 0);
                loop = loop + 1;

            }

        }

    }


      }
        if (sourceentity instanceof Player dplayer) {
            ItemStack mainHandItem = dplayer.getMainHandItem();
            if (!mainHandItem.isEmpty() && mainHandItem.getItem() == ModItems.WAND_OF_CLOUDS.get() && !dplayer.getCooldowns().isOnCooldown(mainHandItem.getItem())) {
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.ENCHANTMENT_TABLE_USE,SoundSource.PLAYERS);
                    } else {

                        _level.playLocalSound(x, y, z, SoundEvents.ENCHANTMENT_TABLE_USE,SoundSource.PLAYERS, 2, 0.5f, false); }
                }
            }}
    }
    }
