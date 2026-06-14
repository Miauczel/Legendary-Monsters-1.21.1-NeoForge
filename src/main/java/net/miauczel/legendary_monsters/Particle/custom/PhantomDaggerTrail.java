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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PhantomDaggerTrail extends AbstractNotGlowingTrailParticle{
    private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/particle/trail_soul.png");

    private final int EntityId;
    private final float width;
    private final float height;
    private final float initialYRot;
    private final float rotateByAge;

    public PhantomDaggerTrail(ClientLevel world, double x, double y, double z, float r, float g, float b, float width, float height, int EntityId) {
        super(world, x, y, z, 0, 0, 0,r,g,b);
        this.EntityId = EntityId;
        this.gravity = 0;
        this.lifetime = 20 + this.random.nextInt(20);
        initialYRot = random.nextFloat() * 360F;
        rotateByAge = (10 + random.nextFloat() * 10F) * (random.nextBoolean() ? -1F : 1F);
        this.width = width;
        this.height = height;
        Vec3 vec3 = getOrbitPosition();
        this.x = this.xo = vec3.x;
        this.y = this.yo = vec3.y;
        this.z = this.zo = vec3.z;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }



    public Vec3 getEntityPosition(){
        Entity from = this.getFromEntity();
        if(from != null){
            return from.position();
        }
        return new Vec3(this.x, this.y, this.z);
    }


    public Entity getFromEntity() {
        return EntityId == -1 ? null : level.getEntity(EntityId);
    }

    public Vec3 getOrbitPosition(){
        Vec3 dinoPos = getEntityPosition();
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

        Entity from = this.getFromEntity();
        if(from == null){
            remove();
        }

    }

    public int sampleCount() {
        return 4;
    }

    public int sampleStep() {
        return 1;
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
    public static final class OrbFactory implements ParticleProvider<OrbData> {

        @Override
        public Particle createParticle(OrbData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PhantomDaggerTrail particle;
            particle = new PhantomDaggerTrail(worldIn, x, y, z, typeIn.getR(), typeIn.getG(), typeIn.getB(), typeIn.getWidth(), typeIn.getHeight(),typeIn.getEntityId());

            return particle;
        }
    }


    public static class OrbData implements ParticleOptions {
        public static final MapCodec<OrbData> CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.FLOAT.fieldOf("r").forGetter(OrbData::getR),
                        Codec.FLOAT.fieldOf("g").forGetter(OrbData::getG),
                        Codec.FLOAT.fieldOf("b").forGetter(OrbData::getB),
                        Codec.FLOAT.fieldOf("width").forGetter(OrbData::getWidth),
                        Codec.FLOAT.fieldOf("height").forGetter(OrbData::getHeight),
                        Codec.INT.fieldOf("entityid").forGetter(OrbData::getEntityId)
                ).apply(instance, OrbData::new));

        public static final StreamCodec<ByteBuf, OrbData> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.FLOAT,
                        OrbData::getR,
                        ByteBufCodecs.FLOAT,
                        OrbData::getG,
                        ByteBufCodecs.FLOAT,
                        OrbData::getB,
                        ByteBufCodecs.FLOAT,
                        OrbData::getWidth,
                        ByteBufCodecs.FLOAT,
                        OrbData::getHeight,
                        ByteBufCodecs.INT,
                        OrbData::getEntityId,
                        OrbData::new
                );

        private final float r;
        private final float g;
        private final float b;
        private final float width;
        private final float height;
        private final int entityId;

        public OrbData(float r, float g, float b, float width, float height, int entityId) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.width = width;
            this.height = height;
            this.entityId = entityId;
        }

        @Override
        public ParticleType<OrbData> getType() {
            return ModParticles.PHANTOM_DAGGER_TRAIL.get();
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

        public int getEntityId() {
            return this.entityId;
        }
    }

    public static class OrbType extends ParticleType<OrbData> {
        public OrbType(boolean overrideLimiter) {
            super(overrideLimiter);
        }

        @Override
        public MapCodec<OrbData> codec() {
            return OrbData.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, OrbData> streamCodec() {
            return OrbData.STREAM_CODEC;
        }
    }
}
