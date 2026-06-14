package net.miauczel.legendary_monsters;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class CommonProxy {
    public Object getModBlockEntityWithoutLevelRenderer() {
        return null;
    }
    public void releaseRenderingEntity(UUID id) {
    }
    public void blockRenderingEntity(UUID id) {
    }
    public boolean isFirstPersonPlayer(Entity entity) {
        return false;
    }
    public static Map<UUID, Integer> bossBarRenderTypes = new HashMap<>();

    public CommonProxy() {
    }
    public Object getISTERProperties() {
        return null;
    }
    public Player getClientSidePlayer() {
        return null;
    }

    public boolean isKeyDown(int keyType) {
        return false;
    }

    public Object getItemRender() {
        return null;
    }
    public void init(IEventBus bus) {
    }
    public void clientInit() {
    }
    public void removeBossBarRender(int bossBar) {
    }

    public void setBossBarRender(UUID bossBar, int renderType) {
    }
}
