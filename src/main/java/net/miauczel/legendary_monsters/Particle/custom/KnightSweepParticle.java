package net.miauczel.legendary_monsters.Particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class KnightSweepParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    public float r, g, b;
    public float yaw;
    public float pitch;

    public KnightSweepParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double xd, double yd, double zd, float scale, float yaw, float pitch, float r, float g, float b) {
        super(pLevel, pX, pY, pZ, 0, 0, 0);

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.r = r;
        this.g = g;
        this.b = b;
        this.lifetime = 9;
        this.gravity = 0;
        sprites = spriteSet;

        this.quadSize = scale * 3.25f;
        this.yaw = yaw;
        this.pitch = pitch;

        this.friction = 1;
    }

    private Vec3 vec3Copy(Vector3f vector3f) {
        return new Vec3(vector3f.x, vector3f.y, vector3f.z);
    }

    @Override
    public void tick() {
        if (this.age++ > this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(sprites);
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        Vec3 Vector3d = camera.getPosition();
        float f = (float) (Mth.lerp(partialTicks, this.xo, this.x) - Vector3d.x());
        float f1 = (float) (Mth.lerp(partialTicks, this.yo, this.y) - Vector3d.y());
        float f2 = (float) (Mth.lerp(partialTicks, this.zo, this.z) - Vector3d.z());
        Quaternionf quaternionf = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
        Quaternionf quatX = MathUtils.quatFromRotationXYZ(pitch, 0, 0, false);
        Quaternionf quatY = MathUtils.quatFromRotationXYZ(0, yaw, 0, false);
        quaternionf.mul(quatY);
        quaternionf.mul(quatX);

        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        quaternionf.transform(vector3f1);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = this.getQuadSize(partialTicks);

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            quaternionf.transform(vector3f);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        float f7 = this.getU0();
        float f8 = this.getU1();
        float f5 = this.getV0();
        float f6 = this.getV1();
        int j = this.getLightColor(partialTicks);
        buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).setUv(f8, f6).setColor(r, g, b, this.alpha).setLight(j);
        buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).setUv(f8, f5).setColor(r, g, b, this.alpha).setLight(j);
        buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).setUv(f7, f5).setColor(r, g, b, this.alpha).setLight(j);
        buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).setUv(f7, f6).setColor(r, g, b, this.alpha).setLight(j);

        buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).setUv(f7, f6).setColor(r, g, b, this.alpha).setLight(j);
        buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).setUv(f7, f5).setColor(r, g, b, this.alpha).setLight(j);
        buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).setUv(f8, f5).setColor(r, g, b, this.alpha).setLight(j);
        buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).setUv(f8, f6).setColor(r, g, b, this.alpha).setLight(j);
    }

    @NotNull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SweepData> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprite) {
            this.sprite = pSprite;
        }

        public Particle createParticle(SweepData options, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            KnightSweepParticle shriekparticle = new KnightSweepParticle(worldIn, x, y, z, sprite, xSpeed, ySpeed, zSpeed, options.getScale(), options.getYaw(), options.getPitch(),options.r,options.g,options.b);
            shriekparticle.setSpriteFromAge(this.sprite);
            shriekparticle.setAlpha(1.0F);
            return shriekparticle;
        }
    }

    public static class SweepData implements ParticleOptions {
        public static final StreamCodec<ByteBuf, KnightSweepParticle.SweepData> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.FLOAT,
                        KnightSweepParticle.SweepData::getScale,
                        ByteBufCodecs.FLOAT,
                        KnightSweepParticle.SweepData::getYaw,
                        ByteBufCodecs.FLOAT,
                        KnightSweepParticle.SweepData::getPitch,
                        ByteBufCodecs.FLOAT,
                        KnightSweepParticle.SweepData::getR,
                        ByteBufCodecs.FLOAT,
                        KnightSweepParticle.SweepData::getG,
                        ByteBufCodecs.FLOAT,
                        KnightSweepParticle.SweepData::getB,
                        KnightSweepParticle.SweepData::new
                );


        private final float scale;
        private final float yaw;
        private final float pitch;

        private final float r;
        private final float g;
        private final float b;
        public SweepData(float scale, float yaw, float pitch,float r,float g, float b) {
            this.scale = scale;
            this.yaw = yaw;
            this.pitch = pitch;
            this.r = r;
            this.g = g;
            this.b = b;
        }
        
        @Override
        public ParticleType<SweepData> getType() {
            return ModParticles.KNIGHTS_SWEEP.get();
        }

        public float getScale() {
            return this.scale;
        }

        public float getYaw() {
            return this.yaw;
        }

        public float getPitch() {
            return this.pitch;
        }

        public float getR() {
            return this.r;
        }

        public float getG() {
            return this.g;
        }

        public float getB() {
            return this.b;
        }

        public static MapCodec<SweepData> CODEC() {
            return RecordCodecBuilder.mapCodec((codecBuilder) -> codecBuilder.group(
                    Codec.FLOAT.fieldOf("scale").forGetter(SweepData::getScale),
                    Codec.FLOAT.fieldOf("yaw").forGetter(SweepData::getYaw),
                    Codec.FLOAT.fieldOf("pitch").forGetter(SweepData::getPitch),
                    Codec.FLOAT.fieldOf("r").forGetter(SweepData::getR),
                    Codec.FLOAT.fieldOf("g").forGetter(SweepData::getG),
                    Codec.FLOAT.fieldOf("b").forGetter(SweepData::getB)
                            ).apply(codecBuilder, SweepData::new)
            );
        }
    }
    public static class OrbType extends ParticleType<KnightSweepParticle.SweepData> {
        public OrbType(boolean overrideLimiter) {
            super(overrideLimiter);
        }

        @Override
        public MapCodec<KnightSweepParticle.SweepData> codec() {
            return KnightSweepParticle.SweepData.CODEC();
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, KnightSweepParticle.SweepData> streamCodec() {
            return KnightSweepParticle.SweepData.STREAM_CODEC;
        }
    }
}