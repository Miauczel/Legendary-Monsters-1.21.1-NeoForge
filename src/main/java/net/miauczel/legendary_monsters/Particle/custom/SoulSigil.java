package net.miauczel.legendary_monsters.Particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class SoulSigil extends TextureSheetParticle {
    public float r;
    public float g;
    public float b;
    public float opacity;
    public boolean facesCamera;
    public float yaw;
    public float pitch;
    public float size;

    private final SpriteSet sprites;
    private final SoulSigil.EnumRingBehavior behavior;

    public enum EnumRingBehavior {
        SHRINK,
        GROW,
        CONSTANT,
        GROW_THEN_SHRINK;

        public static SoulSigil.EnumRingBehavior byName(String name) {
            try {
                return SoulSigil.EnumRingBehavior.valueOf(name.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                return GROW;
            }
        }

        public static SoulSigil.EnumRingBehavior byId(int id) {
            SoulSigil.EnumRingBehavior[] values = SoulSigil.EnumRingBehavior.values();
            return values[Math.floorMod(id, values.length)];
        }
    }

    public SoulSigil(
            ClientLevel world,
            double x,
            double y,
            double z,
            double motionX,
            double motionY,
            double motionZ,
            float yaw,
            float pitch,
            int duration,
            float r,
            float g,
            float b,
            float opacity,
            float size,
            boolean facesCamera,
            SoulSigil.EnumRingBehavior behavior,
            SpriteSet sprites
    ) {
        super(world, x, y, z);

        this.sprites = sprites;
        this.setSize(1, 1);
        this.setSpriteFromAge(this.sprites);

        this.size = size * 0.1f;
        this.lifetime = Math.max(1, duration);
        this.alpha = 1.0F;

        this.r = r;
        this.g = g;
        this.b = b;
        this.opacity = opacity;

        this.yaw = yaw;
        this.pitch = pitch;
        this.facesCamera = facesCamera;

        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;

        this.behavior = behavior;
    }

    @Override
    public int getLightColor(float delta) {
        return 240 | super.getLightColor(delta) & 0xFF0000;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        float lifeProgress = Mth.clamp((this.age + partialTicks) / (float) this.lifetime, 0.0F, 1.0F);

        if (this.behavior == SoulSigil.EnumRingBehavior.GROW) {
            this.quadSize = this.size * lifeProgress;
        } else if (this.behavior == SoulSigil.EnumRingBehavior.SHRINK) {
            this.quadSize = this.size * (1.0F - lifeProgress);
        } else if (this.behavior == SoulSigil.EnumRingBehavior.GROW_THEN_SHRINK) {
            this.quadSize = Math.max(
                    0.0F,
                    (float) (this.size * (1.0F - lifeProgress - Math.pow(2000, -lifeProgress)))
            );
        } else {
            this.quadSize = this.size;
        }

        this.alpha = this.opacity * 0.95F * (1.0F - lifeProgress) + 0.05F;
        this.rCol = this.r;
        this.gCol = this.g;
        this.bCol = this.b;

        Vec3 cameraPos = renderInfo.getPosition();

        float f = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cameraPos.x());
        float f1 = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cameraPos.y());
        float f2 = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cameraPos.z());

        Quaternionf quaternionf = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);

        if (this.facesCamera) {
            if (this.roll == 0.0F) {
                quaternionf = renderInfo.rotation();
            } else {
                quaternionf = new Quaternionf(renderInfo.rotation());
                float roll = Mth.lerp(partialTicks, this.oRoll, this.roll);
                quaternionf.mul(Axis.ZP.rotation(roll));
            }
        } else {
            Quaternionf quatX = MathUtils.quatFromRotationXYZ(this.pitch, 0, 0, false);
            Quaternionf quatY = MathUtils.quatFromRotationXYZ(0, this.yaw, 0, false);

            quaternionf.mul(quatY);
            quaternionf.mul(quatX);
        }

        Vector3f[] vertices = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };

        float quadSize = this.getQuadSize(partialTicks);

        for (int i = 0; i < 4; ++i) {
            Vector3f vertex = vertices[i];
            quaternionf.transform(vertex);
            vertex.mul(quadSize);
            vertex.add(f, f1, f2);
        }

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();

        int light = this.getLightColor(partialTicks);

        buffer.addVertex(vertices[0].x(), vertices[0].y(), vertices[0].z())
                .setUv(u1, v1)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);

        buffer.addVertex(vertices[1].x(), vertices[1].y(), vertices[1].z())
                .setUv(u1, v0)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);

        buffer.addVertex(vertices[2].x(), vertices[2].y(), vertices[2].z())
                .setUv(u0, v0)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);

        buffer.addVertex(vertices[3].x(), vertices[3].y(), vertices[3].z())
                .setUv(u0, v1)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);

        buffer.addVertex(vertices[3].x(), vertices[3].y(), vertices[3].z())
                .setUv(u0, v1)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);

        buffer.addVertex(vertices[2].x(), vertices[2].y(), vertices[2].z())
                .setUv(u0, v0)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);

        buffer.addVertex(vertices[1].x(), vertices[1].y(), vertices[1].z())
                .setUv(u1, v0)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);

        buffer.addVertex(vertices[0].x(), vertices[0].y(), vertices[0].z())
                .setUv(u1, v1)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static final class RingFactory implements ParticleProvider<SoulSigil.RingData> {
        private final SpriteSet spriteSet;

        public RingFactory(SpriteSet sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(
                SoulSigil.RingData typeIn,
                ClientLevel worldIn,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            return new SoulSigil(
                    worldIn,
                    x,
                    y,
                    z,
                    xSpeed,
                    ySpeed,
                    zSpeed,
                    typeIn.getYaw(),
                    typeIn.getPitch(),
                    typeIn.getDuration(),
                    typeIn.getR(),
                    typeIn.getG(),
                    typeIn.getB(),
                    typeIn.getA(),
                    typeIn.getScale(),
                    typeIn.getFacesCamera(),
                    typeIn.getBehavior(),
                    this.spriteSet
            );
        }
    }

    public static class RingData implements ParticleOptions {
        private static final Codec<SoulSigil.EnumRingBehavior> BEHAVIOR_CODEC =
                Codec.STRING.xmap(SoulSigil.EnumRingBehavior::byName, SoulSigil.EnumRingBehavior::name);

        public static final MapCodec<SoulSigil.RingData> CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.FLOAT.fieldOf("yaw").forGetter(SoulSigil.RingData::getYaw),
                        Codec.FLOAT.fieldOf("pitch").forGetter(SoulSigil.RingData::getPitch),
                        Codec.INT.fieldOf("duration").forGetter(SoulSigil.RingData::getDuration),
                        Codec.FLOAT.fieldOf("r").forGetter(SoulSigil.RingData::getR),
                        Codec.FLOAT.fieldOf("g").forGetter(SoulSigil.RingData::getG),
                        Codec.FLOAT.fieldOf("b").forGetter(SoulSigil.RingData::getB),
                        Codec.FLOAT.fieldOf("a").forGetter(SoulSigil.RingData::getA),
                        Codec.FLOAT.fieldOf("scale").forGetter(SoulSigil.RingData::getScale),
                        Codec.BOOL.fieldOf("facesCamera").forGetter(SoulSigil.RingData::getFacesCamera),
                        BEHAVIOR_CODEC.optionalFieldOf("behavior", SoulSigil.EnumRingBehavior.GROW)
                                .forGetter(SoulSigil.RingData::getBehavior)
                ).apply(instance, SoulSigil.RingData::new));

        public static final StreamCodec<ByteBuf, SoulSigil.RingData> STREAM_CODEC =
                StreamCodec.ofMember(SoulSigil.RingData::writeToBuffer, SoulSigil.RingData::readFromBuffer);

        private final float yaw;
        private final float pitch;
        private final int duration;
        private final float r;
        private final float g;
        private final float b;
        private final float a;
        private final float scale;
        private final boolean facesCamera;
        private final SoulSigil.EnumRingBehavior behavior;

        public RingData(
                float yaw,
                float pitch,
                int duration,
                float r,
                float g,
                float b,
                float a,
                float scale,
                boolean facesCamera,
                SoulSigil.EnumRingBehavior behavior
        ) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.duration = duration;
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
            this.scale = scale;
            this.facesCamera = facesCamera;
            this.behavior = behavior;
        }

        private static SoulSigil.RingData readFromBuffer(ByteBuf buffer) {
            return new SoulSigil.RingData(
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readInt(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readBoolean(),
                    SoulSigil.EnumRingBehavior.byId(buffer.readInt())
            );
        }

        private void writeToBuffer(ByteBuf buffer) {
            buffer.writeFloat(this.yaw);
            buffer.writeFloat(this.pitch);
            buffer.writeInt(this.duration);
            buffer.writeFloat(this.r);
            buffer.writeFloat(this.g);
            buffer.writeFloat(this.b);
            buffer.writeFloat(this.a);
            buffer.writeFloat(this.scale);
            buffer.writeBoolean(this.facesCamera);
            buffer.writeInt(this.behavior.ordinal());
        }

        @Override
        public ParticleType<SoulSigil.RingData> getType() {
            return ModParticles.SOUL_SIGIL.get();
        }

        public float getYaw() {
            return this.yaw;
        }

        public float getPitch() {
            return this.pitch;
        }

        public int getDuration() {
            return this.duration;
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

        public float getA() {
            return this.a;
        }

        public float getScale() {
            return this.scale;
        }

        public boolean getFacesCamera() {
            return this.facesCamera;
        }

        public SoulSigil.EnumRingBehavior getBehavior() {
            return this.behavior;
        }
    }

    public static class RingType extends ParticleType<SoulSigil.RingData> {
        public RingType(boolean overrideLimiter) {
            super(overrideLimiter);
        }

        @Override
        public MapCodec<SoulSigil.RingData> codec() {
            return SoulSigil.RingData.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SoulSigil.RingData> streamCodec() {
            return SoulSigil.RingData.STREAM_CODEC;
        }
    }
}