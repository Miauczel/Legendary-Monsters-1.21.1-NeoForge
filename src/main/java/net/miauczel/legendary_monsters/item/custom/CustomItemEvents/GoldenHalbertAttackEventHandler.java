package net.miauczel.legendary_monsters.item.custom.CustomItemEvents;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.WrittenBookItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = LegendaryMonsters.MOD_ID)
public class GoldenHalbertAttackEventHandler {
    @SubscribeEvent
    public static void onEntityHit(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            if (player.getMainHandItem().getItem() == ModItems.GOLDEN_HALBERT.get()) {
                if (event.getEntity() instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity) event.getEntity();
                    if (player.getAttackStrengthScale(0.5F) >= 1.0F) {
                        if (player.fallDistance > 0.0F) {
                            if (Math.random() < 0.25) {
                                target.addEffect(new MobEffectInstance((Holder<MobEffect>) ModEffects.BLEEDING, 70, 0));
                            }
                        }
                    }
                }
            }
        }
    }
}
