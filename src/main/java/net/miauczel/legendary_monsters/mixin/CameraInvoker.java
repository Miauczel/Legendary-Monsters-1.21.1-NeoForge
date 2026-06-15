package net.miauczel.legendary_monsters.mixin;

import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.checkerframework.common.reflection.qual.Invoke;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(Camera.class)
public interface CameraInvoker {

    @Invoker("setPosition")
    void lm$setPosition(double x, double y, double z);

    @Invoker("setRotation")
    void lm$setRotation(float yaw, float pitch, float roll);

    @Invoker("move")
    void lm$move(float zoom, float dy, float dx);

    @Invoker("getMaxZoom")
    float lm$getMaxZoom(float maxZoom);
}






