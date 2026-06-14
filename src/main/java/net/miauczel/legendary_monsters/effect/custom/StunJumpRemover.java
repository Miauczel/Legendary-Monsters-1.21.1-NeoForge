package net.miauczel.legendary_monsters.effect.custom;

import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber
public class StunJumpRemover {
    @SubscribeEvent
    public static void onLivingJump(net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect((Holder<MobEffect>) ModEffects.STUN)) {
            entity.setDeltaMovement(0, 0, 0);
        }
    }
}

