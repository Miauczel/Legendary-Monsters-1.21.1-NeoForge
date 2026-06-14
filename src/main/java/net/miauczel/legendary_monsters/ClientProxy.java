package net.miauczel.legendary_monsters;

import net.miauczel.legendary_monsters.client.ModBlockEntityWithoutLevelRenderer;
import net.miauczel.legendary_monsters.client.ModClientItemExtensions;
import net.miauczel.legendary_monsters.keyBind.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import java.util.*;
import java.util.concurrent.Callable;

public class ClientProxy extends CommonProxy {

    private final ModClientItemExtensions isterProperties = new ModClientItemExtensions();
    /* public static void registerParticleFactories(RegisterParticleProvidersEvent event) {

         Minecraft.getInstance().particleEngine.register(ModParticles.BEAM.get(),
                 Beam.Provider::new);
         Minecraft.getInstance().particleEngine.register(ModParticles.TORNADO.get(),
                 Tornado.Provider::new);
         Minecraft.getInstance().particleEngine.register(ModParticles.TORNADO_FIRE.get(),
                 Tornado.Provider::new);
         Minecraft.getInstance().particleEngine.register(ModParticles.RING.get(),
                 Circle.RingFactory::new);
         Minecraft.getInstance().particleEngine.register(ModParticles.LB.get(),
                 LightningBolt.Factory::new);
         Minecraft.getInstance().particleEngine.register(ModParticles.FLAME.get(),
                 Flame.Provider::new);
         Minecraft.getInstance().particleEngine.register(ModParticles.FLAME_STATIC.get(),
                 Flame_Static.Provider::new);
     }
 */
    public static List<UUID> blockedEntityRenders = new ArrayList<>();

    public void releaseRenderingEntity(UUID id) {
        blockedEntityRenders.remove(id);
    }

    public boolean isFirstPersonPlayer(Entity entity) {
        return entity.equals(Minecraft.getInstance().cameraEntity) && Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    public void blockRenderingEntity(UUID id) {
        blockedEntityRenders.add(id);
    }

    public void init(IEventBus iEventBus) {
       iEventBus.addListener(this::registerKeybinds);
    }

    @OnlyIn(Dist.CLIENT)
    public static Callable<BlockEntityWithoutLevelRenderer> getItemRenderer() {
        return ModBlockEntityWithoutLevelRenderer::new;
    }
    @Override
    public Object getItemRender() {
        return new ModBlockEntityWithoutLevelRenderer();
    }
    public void clientInit() {

        //   FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::registerParticleFactories);
    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    public boolean isKeyDown(int keyType) {
        if (keyType != -1) {
            if (keyType == 4) {
                return ModKeybinds.MOSSY.isDown();
            } else if (keyType == 7) {
                return ModKeybinds.FIERY.isDown();
            } else if (keyType == 4) {

                return ModKeybinds.SKELORAPTOR_ROAR.isDown();

            } else if (keyType == 5) {

                return ModKeybinds.SKELORAPTOR_ATTACK.isDown();

            } else if (keyType == 6) {
                return ModKeybinds.HELMET.isDown();
            }
        } else {
            return Minecraft.getInstance().options.keyLeft.isDown() || Minecraft.getInstance().options.keyRight.isDown() || Minecraft.getInstance().options.keyUp.isDown() || Minecraft.getInstance().options.keyDown.isDown() || Minecraft.getInstance().options.keyJump.isDown();
        }
        return Minecraft.getInstance().options.keyLeft.isDown() || Minecraft.getInstance().options.keyRight.isDown() || Minecraft.getInstance().options.keyUp.isDown() || Minecraft.getInstance().options.keyDown.isDown() || Minecraft.getInstance().options.keyJump.isDown();
    }

    public void removeBossBarRender(int bossBar) {
        bossBarRenderTypes.remove(bossBar);
    }


    private void registerKeybinds(RegisterKeyMappingsEvent e) {
        e.register(ModKeybinds.FIERY);
        e.register(ModKeybinds.MOSSY);
        e.register(ModKeybinds.SKELORAPTOR_ROAR);

        e.register(ModKeybinds.HELMET);
    }
    @Override
    public Object getISTERProperties() {
        return isterProperties;
    }

    @Override
    public void setBossBarRender(UUID bossBar, int renderType) {
        bossBarRenderTypes.put(bossBar, renderType);
    }

    @OnlyIn(Dist.CLIENT)
    public static Callable<BlockEntityWithoutLevelRenderer> ModBlockRenderer() {
        return ModBlockEntityWithoutLevelRenderer::new;
    }

    @Override
    public Object getModBlockEntityWithoutLevelRenderer() {
        return new ModBlockEntityWithoutLevelRenderer();
    }
}