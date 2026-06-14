package net.miauczel.legendary_monsters.client;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class ModItemProperties implements net.neoforged.neoforge.client.extensions.common.IClientItemExtensions {
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new ModBlockEntityWithoutLevelRenderer();
    }
}
