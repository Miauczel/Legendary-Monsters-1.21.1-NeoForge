package net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IAnimatedMob extends IAnimatedMonster {


    protected IAnimatedMob(EntityType<? extends IAnimatedMob> entityType, Level level) {
        super(entityType, level);
    }

    public LivingEntity target() {
        return getTarget();
    }

    @Override
    public ItemEntity spawnAtLocation(ItemStack stack) {
        ItemEntity itementity = this.spawnAtLocation(stack, 0.0f);
        if (itementity != null) {
            itementity.setGlowingTag(true);
            itementity.setExtendedLifetime();
        }
        return itementity;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.FALL)) return false;
        if (pSource.is(DamageTypes.IN_WALL)) return false;
        return super.hurt(pSource, pAmount);

    }
}
