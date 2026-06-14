package net.miauczel.legendary_monsters.client;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ModClientItemExtensions implements IClientItemExtensions {
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new ModBlockEntityWithoutLevelRenderer();
    }
}
