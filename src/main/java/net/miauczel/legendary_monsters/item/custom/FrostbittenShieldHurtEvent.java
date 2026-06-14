package net.miauczel.legendary_monsters.item.custom;

import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;


import javax.annotation.Nullable;

@EventBusSubscriber

public class FrostbittenShieldHurtEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityAttacked(LivingIncomingDamageEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null)
            return;
        if (sourceentity instanceof LivingEntity livingSourceEntity && entity instanceof LivingEntity _livEnt && _livEnt.isBlocking()) {
            if (_livEnt.getMainHandItem().getItem() == ModItems.FROSTBITTEN_SHIELD.get() || _livEnt.getOffhandItem().getItem() == ModItems.FROSTBITTEN_SHIELD.get()) {
                // sourceentity.setTicksFrozen(250);
                livingSourceEntity.addEffect(new MobEffectInstance((Holder<MobEffect>) ModEffects.FREEZE, 60, 0));
            }
        }
    }
}


