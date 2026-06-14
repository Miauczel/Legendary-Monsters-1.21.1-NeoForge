package net.miauczel.legendary_monsters.entity.client.Model.BlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.miauczel.legendary_monsters.block.blockentity.SwingingAxeBlockEntity;
import net.miauczel.legendary_monsters.util.IK_Util;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class SwingingAxeModel extends HierarchicalModel<Entity> {
    public final ModelPart root;
    public final ModelPart chain;
    public final ModelPart axe;

    public SwingingAxeModel(ModelPart root) {
        this.root = root.getChild("root");
        this.chain = this.root.getChild("chain");
        this.axe = this.chain.getChild("axe");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 36).addBox(-4.0F, -2.0F, -1.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 53).addBox(-3.0F, 9.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -3.0F));

        PartDefinition cube_r1 = root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 36).addBox(-4.0F, -4.0F, -5.5F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 4.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition chain = root.addOrReplaceChild("chain", CubeListBuilder.create().texOffs(0, 55).addBox(-2.6667F, -0.5F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3333F, 13.5F, 3.0F));

        PartDefinition cube_r2 = chain.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 55).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3333F, 8.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition axe = chain.addOrReplaceChild("axe", CubeListBuilder.create().texOffs(0, 0).addBox(-23.0F, -0.75F, 0.0F, 46.0F, 36.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3333F, 15.25F, 0.0F));

        PartDefinition cube_r3 = axe.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(12, 55).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(Entity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
    }

    public void animateBlock(SwingingAxeBlockEntity swingingAxeBlockEntity, float partialTicks) {
        Vec3 start = swingingAxeBlockEntity.IK_START();
        Vec3 endPoint = swingingAxeBlockEntity.IK_Point();
        IK_Util.solve2BoneIK(chain, axe, start, endPoint, 0.75f, 1, 1.57f, 0,
                !swingingAxeBlockEntity.isSwingingOffset() ? 1.57f : 0, swingingAxeBlockEntity.isSwingingOffset() ? 1.57f : 0, false);
    }
}