package net.miauczel.legendary_monsters.Particle.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class MovingTrailParticle extends AbstractTrailParticle{
    private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/particle/trail.png");

    private final float width;
    private final float height;
    private final float initialYRot;
    private final float rotateByAge;


    public MovingTrailParticle(ClientLevel world, double x, double y, double z, float r, float g, float b, float width, float height) {
        super(world, x, y, z, 0, 0, 0,r,g,b);
        this.gravity = 0;

        this.lifetime = 20 + this.random.nextInt(20);
        initialYRot = random.nextFloat() * 360F;
        rotateByAge = (10 + random.nextFloat() * 10F) * (random.nextBoolean() ? -1F : 1F);
        this.width = width;
        this.height = height;
        Vec3 vec3 = getOrbitPosition();
        this.x = this.xo = vec3.x;
        //this.y = this.yo = vec3.y;
        this.z = this.zo = vec3.z;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }



    public Vec3 getEntityPosition(){
        Particle from = this;
            return from.getPos();
    }
    public Vec3 getOrbitPosition(){

        Vec3 dinoPos = getPos();
        Vec3 vec3 = new Vec3(0, height, width).yRot((float)Math.toRadians(initialYRot + rotateByAge * age));
        return dinoPos.add(vec3);
    }

    public void tick() {
        super.tick();

        float fade = 1F - age / (float) lifetime;
        this.trailA = 1F * fade;
        Vec3 vec3 = getOrbitPosition();
        this.x = vec3.x;
        this.y = vec3.y;
        this.z = vec3.z;

    }

    public int sampleCount() {
        return 4;
    }

    public int sampleStep() {
        return 2;
    }

    @Override
    public float getTrailHeight() {
        return 0.5F;
    }

    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public ResourceLocation getTrailTexture() {
        return TRAIL_TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    public static final class OrbFactory implements ParticleProvider<TrailData> {

        @Override
        public Particle createParticle(TrailData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MovingTrailParticle particle;
            particle = new MovingTrailParticle(worldIn, x, y, z, typeIn.getR(), typeIn.getG(), typeIn.getB(), typeIn.getWidth(), typeIn.getHeight());

            return particle;
        }
    }


    public static class TrailData implements ParticleOptions {
        public static final MapCodec<TrailData> CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.FLOAT.fieldOf("r").forGetter(TrailData::getR),
                        Codec.FLOAT.fieldOf("g").forGetter(TrailData::getG),
                        Codec.FLOAT.fieldOf("b").forGetter(TrailData::getB),
                        Codec.FLOAT.fieldOf("width").forGetter(TrailData::getWidth),
                        Codec.FLOAT.fieldOf("height").forGetter(TrailData::getHeight)
                ).apply(instance, TrailData::new));

        public static final StreamCodec<ByteBuf, TrailData> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.FLOAT,
                        TrailData::getR,
                        ByteBufCodecs.FLOAT,
                        TrailData::getG,
                        ByteBufCodecs.FLOAT,
                        TrailData::getB,
                        ByteBufCodecs.FLOAT,
                        TrailData::getWidth,
                        ByteBufCodecs.FLOAT,
                        TrailData::getHeight,
                        TrailData::new
                );

        private final float r;
        private final float g;
        private final float b;
        private final float width;
        private final float height;

        public TrailData(float r, float g, float b, float width, float height) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.width = width;
            this.height = height;
        }

        @Override
        public ParticleType<TrailData> getType() {
            return ModParticles.MOVING_TRAIL.get();
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

        public float getWidth() {
            return this.width;
        }

        public float getHeight() {
            return this.height;
        }
        
    }

    public static class OrbType extends ParticleType<TrailData> {
        public OrbType(boolean overrideLimiter) {
            super(overrideLimiter);
        }

        @Override
        public MapCodec<TrailData> codec() {
            return TrailData.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, TrailData> streamCodec() {
            return TrailData.STREAM_CODEC;
        }
    }

}
