package net.miauczel.legendary_monsters.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.miauczel.legendary_monsters.ClientProxy;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.client.RenderUtils;
import net.miauczel.legendary_monsters.client.event.gui.CustomBossBar;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.DynamicCameraZoomEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.PossessedPaladin.PossessedPaladinEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.TheObliterator.TheObliteratorEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.ShulkerTower.Shulker_MimicEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.AnnihilationPursuer.AnnihilationPursuerEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.BigShulkerBulletEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ThrownEntity.EntityThrownEntity;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.mixin.CameraInvoker;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(
        modid = LegendaryMonsters.MOD_ID,
        value = Dist.CLIENT,
        bus = EventBusSubscriber.Bus.GAME
)
public class ClientEvent {

    public ClientEvent() {
    }

    private static final ResourceLocation[] TYPE_A_TEXTURES = new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_0.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_1.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_2.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_3.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_4.png")
    };

    @SubscribeEvent
    public static void renderPortals(RenderLevelStageEvent event) {

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        Player player = mc.player;
        if (player != null) {
            if (!player.isUsingItem() || player.getMainHandItem().getItem() != ModItems.TESSERACT.get()) return;
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
            PoseStack matrix = event.getPoseStack();
            Camera cam = event.getCamera();
            Vec3 camPos = cam.getPosition();

            float pt = event.getPartialTick().getGameTimeDeltaTicks();

            MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();

            matrix.pushPose();
            matrix.translate(-camPos.x, -camPos.y, -camPos.z);


            double x = Mth.lerp(pt, player.xOld, player.getX());
            double y = Mth.lerp(pt, player.yOld, player.getY());
            double z = Mth.lerp(pt, player.zOld, player.getZ());

            matrix.pushPose();
            matrix.translate(x, y, z);
            ResourceLocation[] texArr = TYPE_A_TEXTURES;
            int total = texArr.length;
            int frame = (int) ((player.tickCount + pt) * 12.0F / 20.0F) % total;
            ResourceLocation tex = texArr[Math.floorMod(frame, total)];

            RenderType rt = RenderType.entityTranslucentEmissive(tex);
            VertexConsumer vertexConsumer = buffer.getBuffer(rt);

            double dy = 0.1f;

            int FULL_BRIGHT = LightTexture.pack(15, 15);

            double bigsize = Mth.clamp((player.getTicksUsingItem() * 2) * 0.1, 0, 4);
            double smallsize = Mth.clamp((player.getTicksUsingItem()) * 0.1, 0, 2);

            RenderUtils.renderQuad((float) bigsize, 0, dy, 0, 90, 0, 0, vertexConsumer, matrix, OverlayTexture.NO_OVERLAY, FULL_BRIGHT);
            for (int k = 0; k < 5; ++k) {
                float f3 = (float) k * (float) Math.PI * 2 / 5 + ((float) Math.PI * 2 / 10F);
                float multiplier = 7.5f;
                RenderUtils.renderQuad((float) smallsize, Mth.cos(f3) * multiplier, dy, Mth.sin(f3) * multiplier, 90, 0, 0, vertexConsumer, matrix, OverlayTexture.NO_OVERLAY, FULL_BRIGHT);
            }
            for (int k = 0; k < 5; ++k) {
                float f3 = (float) k * (float) Math.PI * 2 / 5 + ((float) Math.PI * 2 / 10F);
                float multiplier = 4f;
                RenderUtils.renderQuad((float) smallsize, Mth.cos(f3) * multiplier, dy, Mth.sin(f3) * multiplier, 90, 0, 0, vertexConsumer, matrix, OverlayTexture.NO_OVERLAY, FULL_BRIGHT);
            }

            matrix.popPose();


            matrix.popPose();
            buffer.endBatch();
        }
    }

    @SubscribeEvent
    public static void setZoom(CalculateDetachedCameraDistanceEvent event) {

        Entity camEntity = Minecraft.getInstance().getCameraEntity();
        if (camEntity == null) return;
        if (!event.getCamera().isDetached()) return;
        if (!camEntity.isPassenger()) return;

        Entity vehicle = camEntity.getVehicle();
        if (vehicle == null) return;

        float zoom;
        if (vehicle instanceof SkeloraptorEntity) zoom = 6.0F;
        else if (vehicle instanceof Shulker_MimicEntity
                || vehicle instanceof EntityThrownEntity
                || vehicle instanceof BigShulkerBulletEntity
                || vehicle instanceof AnnihilationPursuerEntity
                || vehicle instanceof PossessedPaladinEntity
                || vehicle instanceof TheObliteratorEntity) zoom = 8.0F;
        else return;


        event.setDistance(zoom);

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void setCameraLockedOn(ViewportEvent.ComputeCameraAngles event) {
        Entity camEntity = Minecraft.getInstance().getCameraEntity();
        CameraInvoker camera = (CameraInvoker) event.getCamera();
        if (camEntity == null) return;
        if (!event.getCamera().isDetached()) return;
        for (DynamicCameraZoomEntity cameraZoom : camEntity.level().getEntitiesOfClass(DynamicCameraZoomEntity.class, camEntity.getBoundingBox().inflate(20, 20, 20))) {

            LivingEntity boss = cameraZoom.getCameraEntity();
            if (cameraZoom.distanceTo(camEntity) < cameraZoom.getRadius() && cameraZoom.getCameraLocked()) {
                double dx = cameraZoom.getX() - camEntity.getX();
                double dy = cameraZoom.getY() - camEntity.getY();
                double dz = cameraZoom.getZ() - camEntity.getZ();
                double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
                double pitch = Math.toDegrees(Math.atan2(dy, horizontalDistance));
                double yaw = Math.toDegrees(Math.atan2(dz, dx));
               /// event.setPitch((float) pitch);
              ///  event.setYaw((float) yaw);
               // camera.lm$setPosition(cameraZoom.getX(), cameraZoom.getY(), cameraZoom.getZ());

            }

        }

    }

    @SubscribeEvent
    public static void setDynamicZoom(CalculateDetachedCameraDistanceEvent event) {
        Entity camEntity = Minecraft.getInstance().getCameraEntity();
        Camera camera = event.getCamera();
        if (camEntity == null) return;
        if (!event.getCamera().isDetached()) return;

        if (ModConfig.MOB_CONFIG.allowCameraZoom.get() && !Minecraft.getInstance().isPaused()) {

            for (DynamicCameraZoomEntity cameraZoom : camEntity.level().getEntitiesOfClass(DynamicCameraZoomEntity.class, camEntity.getBoundingBox().inflate(20, 20, 20))) {

                if (cameraZoom.distanceTo(camEntity) < cameraZoom.getRadius() && camEntity instanceof LivingEntity living) {
                    float finalZoom = Mth.lerp(camera.getPartialTickTime(), cameraZoom.getZoomIncrementOld(), cameraZoom.getZoomIncrement());
                    event.setDistance((4 * living.getScale()) + finalZoom);
                }

            }
        }
    }

    @SubscribeEvent
    public static void onClientTickPre(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        //Camera Change
        boolean isRidingWarp = ((mc.player.getVehicle() instanceof TheObliteratorEntity theWarpedOneEntity)
                && theWarpedOneEntity.getAttackState() != 20 && theWarpedOneEntity.getAttackState() != 19 && theWarpedOneEntity.getAttackState() != 18)
                || mc.player.getVehicle() instanceof EntityThrownEntity bulletEntity && bulletEntity.isVehicle()
                || mc.player.getVehicle() instanceof BigShulkerBulletEntity BigShulkerbulletEntity && BigShulkerbulletEntity.isVehicle()
                || mc.player.getVehicle() instanceof Shulker_MimicEntity ShulkerMimicEntity && ShulkerMimicEntity.isVehicle()
                || mc.player.getVehicle() instanceof AnnihilationPursuerEntity AnnihilationPursuer && AnnihilationPursuer.isVehicle()
                || mc.player.getVehicle() instanceof PossessedPaladinEntity PossessedPaladin && PossessedPaladin.isVehicle();

        if (isRidingWarp && !CameraApplied) {

            previousCameraType = mc.options.getCameraType();
            mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            CameraApplied = true;
        } else if (!isRidingWarp && CameraApplied) {

            mc.options.setCameraType(previousCameraType);
            CameraApplied = false;
        }

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onPreRenderEntity(RenderLivingEvent.Pre event) {
        if (ClientProxy.blockedEntityRenders.contains(event.getEntity().getUUID())) {
            if (!LegendaryMonsters.proxy.isFirstPersonPlayer(event.getEntity())) {
                NeoForge.EVENT_BUS.post(new RenderLivingEvent.Post(event.getEntity(), event.getRenderer(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
                event.setCanceled(true);
            }
            ClientProxy.blockedEntityRenders.remove(event.getEntity().getUUID());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
        float ticksExistedDelta = player.tickCount + delta;

        if (ModConfig.MOB_CONFIG.allowCameraShake.get() && !Minecraft.getInstance().isPaused()) {
            float shakeAmplitude = 0;
            for (CameraShakeEntity cameraShake : player.level().getEntitiesOfClass(CameraShakeEntity.class, player.getBoundingBox().inflate(20, 20, 20))) {
                if (cameraShake.distanceTo(player) < cameraShake.getRadius()) {
                    shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                }
            }
            if (shakeAmplitude > 1.0f) shakeAmplitude = 1.0f;
            event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3 + 2) * 25));
            event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5 + 1) * 25));
            event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4) * 25));
        }

        MobEffectInstance effectInstance = player.getEffect(ModEffects.STUN);
        if (effectInstance != null) {
            float stunShakeAmplitude = (1 + effectInstance.getAmplifier()) * 0.01F;
            event.setPitch((float) (event.getPitch() + stunShakeAmplitude * Math.cos(ticksExistedDelta * 3.0F + 2.0F) * 25.0));
            event.setYaw((float) (event.getYaw() + stunShakeAmplitude * Math.cos(ticksExistedDelta * 5.0F + 1.0F) * 25.0));
            event.setRoll((float) (event.getRoll() + stunShakeAmplitude * Math.cos(ticksExistedDelta * 4.0F) * 25.0));

        }
        MobEffectInstance curseInstance = player.getEffect(ModEffects.PHARAONS_CURSE);
        if (curseInstance != null) {
            event.setRoll(event.getRoll() + 180.0F);
            /**  float rotationSpeed = 10F; // Prędkość obrotu, możesz dostosować według potrzeb
             float rollRotation = (ticksExistedDelta * rotationSpeed) % 360;
             event.setRoll(rollRotation);*/

        }
    }

    private static boolean CameraApplied = false;
    private static CameraType previousCameraType;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void renderBossOverlay(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (!ModConfig.MOB_CONFIG.allowCustomBossBar.get()) return;

        if (ClientProxy.bossBarRenderTypes.containsKey(event.getBossEvent().getId())) {
            int renderTypeFor = ClientProxy.bossBarRenderTypes.get(event.getBossEvent().getId());
            CustomBossBar customBossBar = CustomBossBar.customBossBars.getOrDefault(renderTypeFor, null);
            if (customBossBar == null) return;

            event.setCanceled(true);
            customBossBar.renderBossBar(event);

        }

    }
}

