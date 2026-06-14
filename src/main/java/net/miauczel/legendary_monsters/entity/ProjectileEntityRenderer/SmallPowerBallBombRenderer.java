package net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SmallAnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.miauczel.legendary_monsters.entity.client.Render.LMRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmallPowerBallBombRenderer extends EntityRenderer<SmallAnnihilationBombEntity> {
    public static final ResourceLocation INNER_LAYER = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/the_warped_one/ball/power_ball_inner.png");

    public static final ResourceLocation OUTER_LAYER = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/the_warped_one/ball/power_ball_outer.png");
    private final SmallPowerBallBombModel model;

    public SmallPowerBallBombRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new SmallPowerBallBombModel(pContext.bakeLayer(ModModelLayers.SMALL_POWER_BALL_LAYER));

    }

    @Override
    public void render(SmallAnnihilationBombEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        // pMatrixStack.mulPose((new Quaternionf()).setAngleAxis(pEntityYaw * ((float)Math.PI / 180F), 0, -1.0F, 0));

        model.setupAnim(pEntity, 0,0, pEntity.tickCount + pPartialTicks, 0, 0);
        float uniformScale = 0.5f;
        pMatrixStack.scale(uniformScale, uniformScale, uniformScale);

        VertexConsumer VertexConsumer2 = pBuffer.getBuffer(LMRenderTypes.getGlowEyes(OUTER_LAYER));
        model.renderToBuffer(pMatrixStack, VertexConsumer2, pPackedLight, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(0.4f, 1, 1, 1));
        VertexConsumer VertexConsumer = pBuffer.getBuffer(LMRenderTypes.getGlowEyes(this.getTextureLocation(pEntity)));
        model.renderToBuffer(pMatrixStack, VertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        ;
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(SmallAnnihilationBombEntity pEntity) {
        return INNER_LAYER;
    }
}
