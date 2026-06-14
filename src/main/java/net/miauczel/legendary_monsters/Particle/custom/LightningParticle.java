package net.miauczel.legendary_monsters.Particle.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector4f;

import java.util.Random;

public class LightningParticle extends Particle {


    private final int r,g,b;
    private float toX;
    private float toY;
    private float toZ;
    private LightningRender lightningRender = new LightningRender();

    public LightningParticle(ClientLevel world, double x, double y, double z, float xSpeed, float ySpeed, float zSpeed, int r, int g, int b) {
        super(world, x, y, z);
        this.setSize(1, 1);
        this.gravity = 0.0F;
        this.lifetime = 5 + new Random().nextInt(3);
        this.toX = xSpeed;
        this.toY = ySpeed;
        this.toZ = zSpeed;
        this.r = r;
        this.g = g;
        this.b = b;

    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        Vec3 vec3 = camera.getPosition();
        PoseStack posestack = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float f = (float)(Mth.lerp((double)partialTick, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)partialTick, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)partialTick, this.zo, this.z) - vec3.z());
        float lerpAge = this.age + partialTick;
        float ageProgress = lerpAge / (float) this.lifetime;
        float scale = 1.85F;
        posestack.pushPose();
        posestack.translate(f, f1, f2);
        posestack.scale(scale, scale, scale);
        LightningBoltData.BoltRenderInfo lightningBoltData = new LightningBoltData.BoltRenderInfo(0.5F, 0.1F, 0.5F, 0.85F, new Vector4f((float) r /255, (float) g /255, (float) b /255, (1.0F - ageProgress) * 0.8F), 0.1F);
        LightningBoltData bolt = new LightningBoltData(lightningBoltData, Vec3.ZERO, new Vec3(toX, toY, toZ), 4)
                .size(0.05F)
                .lifespan(this.lifetime)
                .spawn(LightningBoltData.SpawnFunction.CONSECUTIVE);
        lightningRender.update(this, bolt, partialTick);
        lightningRender.render(partialTick, posestack, multibuffersource$buffersource);

        multibuffersource$buffersource.endBatch();
        posestack.popPose();
    }



    @OnlyIn(Dist.CLIENT)
    public static final class OrbFactory implements ParticleProvider<OrbData> {

        @Override
        public Particle createParticle(OrbData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LightningParticle particle;
            particle = new LightningParticle(worldIn, x, y, z, (float)xSpeed, (float)ySpeed, (float)zSpeed, typeIn.getR(), typeIn.getG(), typeIn.getB());

            return particle;
        }
    }

    public static class OrbData implements ParticleOptions {



        private final int r;
        private final int g;
        private final int b;



        public OrbData(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;


        }


        @Override
        public ParticleType<OrbData> getType() {
            return ModParticles.LIGHTNING.get();
        }

        public int getR() {
            return this.r;
        }

        public int getG() {
            return this.g;
        }

        public int getB() {
            return this.b;
        }

        public static final StreamCodec<ByteBuf, OrbData> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.INT,
                        OrbData::getR,
                        ByteBufCodecs.INT,
                        OrbData::getG,
                        ByteBufCodecs.INT,
                        OrbData::getB,
                        OrbData::new
                );

        public static MapCodec<OrbData> CODEC() {
            return RecordCodecBuilder.mapCodec((codecBuilder) -> codecBuilder.group(
                            Codec.INT.fieldOf("r").forGetter(OrbData::getR),
                            Codec.INT.fieldOf("g").forGetter(OrbData::getG),
                            Codec.INT.fieldOf("b").forGetter(OrbData::getB)
                    ).apply(codecBuilder, OrbData::new)
            );
        }

    }
    public static class OrbType extends ParticleType<OrbData> {
        public OrbType(boolean overrideLimiter) {
            super(overrideLimiter);
        }

        @Override
        public MapCodec<OrbData> codec() {
            return OrbData.CODEC();
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, OrbData> streamCodec() {
            return OrbData.STREAM_CODEC;
        }
    }
}