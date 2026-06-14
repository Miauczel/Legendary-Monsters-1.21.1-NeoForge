package net.miauczel.legendary_monsters.entity.client.Render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationPortalEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class AnnihilationPortalRenderer extends EntityRenderer<AnnihilationPortalEntity> {
    private static final ResourceLocation[] TYPE_A_TEXTURES = new ResourceLocation[] {
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_0.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_1.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_2.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_3.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_4.png")
    };
    private static final ResourceLocation[] TYPE_B_TEXTURES = new ResourceLocation[] {
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_0.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_1.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_2.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_3.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_4.png")
    };
    public AnnihilationPortalRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(AnnihilationPortalEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public void render(AnnihilationPortalEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float progress = (float)entity.tickCount*0.05f;
        float scale = Mth.clamp(progress, 0.0f, 1.0f);

float ticks1 = (float) (entity.disappearTicks * 0.2);
float scale1 = Mth.clamp(ticks1,0,1);
        poseStack.pushPose();
        if (entity.matrixScale() == 2 || entity.matrixScale() == 4) {
           poseStack.scale(1,1,1);
        }else {
            poseStack.scale(scale, scale, scale);
        }
        poseStack.translate(0.0D, 0.03D, 0.0D);

        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));

        ResourceLocation[] texArr = entity.isA() ? TYPE_A_TEXTURES : TYPE_B_TEXTURES;
        int total = texArr.length;
        int frame = (int)((entity.tickCount + partialTicks) * 12.0F / 20.0F) % total;
        ResourceLocation tex = texArr[Math.floorMod(frame, total)];

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(tex));
        final int FULL_BRIGHT = LightTexture.pack(15, 15);
        int overlay = OverlayTexture.NO_OVERLAY;

        Matrix4f matrix = poseStack.last().pose();
        PoseStack.Pose normalMat = poseStack.last();
        float half = entity.matrixScale();

        consumer.addVertex(matrix, -half, -half, 0.0F).setColor(255,255,255,255).setUv(0,1).setOverlay(overlay).setLight(FULL_BRIGHT).setNormal(normalMat,0,0,1);
        consumer.addVertex(matrix,  half, -half, 0.0F).setColor(255,255,255,255).setUv(1,1).setOverlay(overlay).setLight(FULL_BRIGHT).setNormal(normalMat,0,0,1);
        consumer.addVertex(matrix,  half,  half, 0.0F).setColor(255,255,255,255).setUv(1,0).setOverlay(overlay).setLight(FULL_BRIGHT).setNormal(normalMat,0,0,1);
        consumer.addVertex(matrix, -half,  half, 0.0F).setColor(255,255,255,255).setUv(0,0).setOverlay(overlay).setLight(FULL_BRIGHT).setNormal(normalMat,0,0,1);

        poseStack.popPose();
    }


    @Override
    public ResourceLocation getTextureLocation(AnnihilationPortalEntity entity) {
        // nie używane, bo bindujemy teksturę ręcznie w render()
        return entity.isA() ? TYPE_A_TEXTURES[0] : TYPE_B_TEXTURES[0];
    }
}
