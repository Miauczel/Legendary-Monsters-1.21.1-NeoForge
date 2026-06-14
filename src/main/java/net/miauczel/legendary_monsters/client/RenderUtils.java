package net.miauczel.legendary_monsters.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderUtils {
    public static void renderQuad(float scale, double x, double y, double z, double xRot, double yRot, double zRot, VertexConsumer consumer, PoseStack poseStack, int overlay, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(x, y, z);
        //Quaternionf quaternionf = new Quaternionf(xRot,yRot,zRot,0);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) yRot));

        poseStack.mulPose(Axis.XP.rotationDegrees((float) -xRot));

        poseStack.mulPose(Axis.ZP.rotationDegrees((float) zRot));
        PoseStack.Pose normalMat = poseStack.last();
        Matrix4f matrix = poseStack.last().pose();
        consumer.addVertex(matrix, -scale, -scale, 0).setColor(255, 255, 255, 255).setUv(0, 1).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, scale, -scale, 0).setColor(255, 255, 255, 255).setUv(1, 1).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, scale, scale, 0).setColor(255, 255, 255, 255).setUv(1, 0).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, -scale, scale, 0).setColor(255, 255, 255, 255).setUv(0, 0).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        poseStack.popPose();
    }

    //With pivot
    public static void renderPivotedQuad(float scale, float scale2, double x, double y, double z, double xRot, double yRot, double zRot,
                                         VertexConsumer consumer, PoseStack poseStack, int overlay, int packedLight, float r, float g, float b, float a) {

        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) yRot));

        poseStack.translate(-scale, 0.0, 0.0);

        poseStack.mulPose(Axis.XP.rotationDegrees((float) -xRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) zRot));
        PoseStack.Pose normalMat = poseStack.last();
        Matrix4f matrix = poseStack.last().pose();

        consumer.addVertex(matrix, -scale, -scale2, 0).setColor(r, g, b, a).setUv(0, 1).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, scale, -scale2, 0).setColor(r, g, b, a).setUv(1, 1).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, scale, scale2, 0).setColor(r, g, b, a).setUv(1, 0).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, -scale, scale2, 0).setColor(r, g, b, a).setUv(0, 0).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);

        poseStack.popPose();
    }

    public static void renderPivotedQuad(float scale, float scale2, double x, double y, double z, double xRot, double yRot, double zRot, float pivotX, float pivotY, float pivotZ,
                                         VertexConsumer consumer, PoseStack poseStack, int overlay, int packedLight, float r, float g, float b, float a) {
        poseStack.pushPose();

        poseStack.translate(x, y, z);

        poseStack.translate(pivotX, pivotY, pivotZ);

        poseStack.mulPose(Axis.YP.rotationDegrees((float) yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) -xRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) zRot));

        poseStack.translate(-pivotX, -pivotY, -pivotZ);

        PoseStack.Pose normalMat = poseStack.last();
        Matrix4f matrix = poseStack.last().pose();

        consumer.addVertex(matrix, -scale, -scale2, 0).setColor(r, g, b, a).setUv(0, 1).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, scale, -scale2, 0).setColor(r, g, b, a).setUv(1, 1).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, scale, scale2, 0).setColor(r, g, b, a).setUv(1, 0).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);
        consumer.addVertex(matrix, -scale, scale2, 0).setColor(r, g, b, a).setUv(0, 0).setOverlay(overlay).setLight(packedLight).setNormal(normalMat, 0, 0, 1);

        poseStack.popPose();
    }

}
