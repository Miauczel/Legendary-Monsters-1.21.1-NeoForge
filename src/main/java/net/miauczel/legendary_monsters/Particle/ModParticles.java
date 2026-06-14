package net.miauczel.legendary_monsters.Particle;

import com.mojang.serialization.Codec;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.Particle.custom.*;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ModParticles {
    public static final net.neoforged.neoforge.registries.DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            net.neoforged.neoforge.registries.DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, LegendaryMonsters.MOD_ID);

    public static final Supplier<SimpleParticleType> WARNING =
            PARTICLE_TYPES.register("warning", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> BEAM =
            PARTICLE_TYPES.register("beam", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> TORNADO =
            PARTICLE_TYPES.register("tornado", () -> new SimpleParticleType(true));


    public static final Supplier<SimpleParticleType> SMALL_SOUL_FIRE_FLAME =
            PARTICLE_TYPES.register("small_soul_fire_flame", () -> new SimpleParticleType(true));


    public static final Supplier<SimpleParticleType> FLAME =
            PARTICLE_TYPES.register("flame", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> FLAME_STATIC =
            PARTICLE_TYPES.register("flame_static", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> CHORUS_SMOKE =
            PARTICLE_TYPES.register("chorus_smoke", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> LB =
            PARTICLE_TYPES.register("bolt", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> TELEPORT_EFFECT =
            PARTICLE_TYPES.register("black_hole", () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, Circle.RingData.OrbType> RING =
            PARTICLE_TYPES.register("circle", () -> new Circle.RingData.OrbType(false));

    public static final DeferredHolder<ParticleType<?>, SoulSigil.RingType> SOUL_SIGIL =
            PARTICLE_TYPES.register("soul_sigil", () -> new SoulSigil.RingType(false));

    public static final Supplier<SimpleParticleType> SOULSTRIKE =
            PARTICLE_TYPES.register("soul_strike", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> SOULSTRIKE_RED =
            PARTICLE_TYPES.register("soul_strike_red", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> TORNADO_FIRE =
            PARTICLE_TYPES.register("tornado_fire", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> GHOSTLY_SOUL=
            PARTICLE_TYPES.register("ghostly_soul", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> GHOSTLY_SOUL_RED=
            PARTICLE_TYPES.register("ghostly_soul_red", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> DIMENSIONAL_BOMB_EXPLOSION=
            PARTICLE_TYPES.register("dimensional_bomb", () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, AnnihilationBombTrail.OrbType> POWER_BOMB_TRAIL =
            PARTICLE_TYPES.register("power_bomb_trail", () -> new AnnihilationBombTrail.OrbType(false));

    public static final DeferredHolder<ParticleType<?>, PhantomDaggerTrail.OrbType> PHANTOM_DAGGER_TRAIL =
            PARTICLE_TYPES.register("phantom_dagger_trail", () -> new PhantomDaggerTrail.OrbType(false));

    public static final DeferredHolder<ParticleType<?>, AirTrailParticle.OrbType> AIR_TRAIL =
            PARTICLE_TYPES.register("air_trail", () -> new AirTrailParticle.OrbType(false));

    public static final DeferredHolder<ParticleType<?>, LaserBulletTrail.OrbType> LASER_BULLET_TRAIL =
            PARTICLE_TYPES.register("laser_bullet_trail", () -> new LaserBulletTrail.OrbType(false));

    public static final DeferredHolder<ParticleType<?>, LightningParticle.OrbType> LIGHTNING =
            PARTICLE_TYPES.register("lightning", () -> new LightningParticle.OrbType(false));


    public static final Supplier<SimpleParticleType> ANNIHILATION_FLAME_STRIKE =
            PARTICLE_TYPES.register("green_fire_strike", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> ANNIHILATION_GEYSER=
            PARTICLE_TYPES.register("annihilation_geyser", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> DIMENSIONAL_EXPLOSION=
            PARTICLE_TYPES.register("dimensional_explosion", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> ANNIHILATION_EXPLOSION=
            PARTICLE_TYPES.register("annihilation_explosion", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> BIG_ANNIHILATION_FLAME =
            PARTICLE_TYPES.register("big_green_flame", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> BIG_ORANGE_FLAME=
            PARTICLE_TYPES.register("big_orange_flame", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> LM_COSY_SMOKE=
            PARTICLE_TYPES.register("lm_cosy_smoke", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SMALL_ANNIHILATION_FLAME =
            PARTICLE_TYPES.register("small_green_flame", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> RED_SOUL_FLAME =
            PARTICLE_TYPES.register("red_soul_flame", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SHULKER_EXPLOSION=
            PARTICLE_TYPES.register("shulker_explosion", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> PURPLE_SHULKER_EXPLOSION =
            PARTICLE_TYPES.register("purple_shulker_explosion", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> GROUNDSOUL =
            PARTICLE_TYPES.register("ground_soul", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> GROUNDSOUL_RED =
            PARTICLE_TYPES.register("ground_soul_red", () -> new SimpleParticleType(true));






    public static final DeferredHolder<ParticleType<?>, KnightSweepParticle.OrbType> KNIGHTS_SWEEP =
            PARTICLE_TYPES.register("knight_sweep", () -> new KnightSweepParticle.OrbType(false));

    public static final DeferredHolder<ParticleType<?>, AnnihilationSweepParticle.SweepData.OrbType> ANNIHILATION_SWEEP_PARTICLE =
            PARTICLE_TYPES.register("green_sweep", () -> new AnnihilationSweepParticle.SweepData.OrbType(false));


    public static final DeferredHolder<ParticleType<?>, SoulSweepParticle.SweepData.OrbType> SOUL_SWEEP =
            PARTICLE_TYPES.register("soul_sweep", () -> new SoulSweepParticle.SweepData.OrbType(false));


    public static final DeferredHolder<ParticleType<?>, SoulSweepRedParticle.SweepData.OrbType> SOUL_SWEEP_RED =
            PARTICLE_TYPES.register("soul_sweep_red", () -> new SoulSweepRedParticle.SweepData.OrbType(false));


    public static final DeferredHolder<ParticleType<?>, GiantAnnihilationSweepParticle.OrbType> GIANT_ANNIHILATION_SWEEP_PARTICLE =
            PARTICLE_TYPES.register("giant_annihilation_sweep", () -> new GiantAnnihilationSweepParticle.OrbType(false));



    public static final DeferredHolder<ParticleType<?>, MovingTrailParticle.OrbType> MOVING_TRAIL =
            PARTICLE_TYPES.register("moving_trail", () -> new MovingTrailParticle.OrbType(false));


    public static final DeferredHolder<ParticleType<?>, BigAnnihilationSweepParticle.SweepData.OrbType> BIG_ANNIHILATION_SWEEP =
            PARTICLE_TYPES.register("big_annihilation_sweep", () -> new BigAnnihilationSweepParticle.SweepData.OrbType(false));

    public static final DeferredHolder<ParticleType<?>, BeheadedKnightSweepParticle.OrbType> BEHEADED_KNIGHT_SWEEP =
            PARTICLE_TYPES.register("beheaded_knight_sweep", () -> new BeheadedKnightSweepParticle.OrbType(false));


    public static final Supplier<SimpleParticleType> ANNIHILATION_NUKE=
            PARTICLE_TYPES.register("annihilation_nuke", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SOUL_EXPLOSION=
            PARTICLE_TYPES.register("soul_explosion", () -> new SimpleParticleType(true));


    public static final Supplier<SimpleParticleType> SOUL_PILLAR_EXPLOSION=
            PARTICLE_TYPES.register("soul_pillar_explosion", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SOUL_EXPLOSION_RED=
            PARTICLE_TYPES.register("soul_explosion_red", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> GROUND_ANNIHILATION_NUKE=
            PARTICLE_TYPES.register("ground_annihilation_nuke", () -> new SimpleParticleType(true));
    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    public static final Supplier<SimpleParticleType> SHULKER_WIND=
            PARTICLE_TYPES.register("shulker_wind", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SHULKER_CONFETTI =
            PARTICLE_TYPES.register("shulker_confetti", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SOUL_SHOOT =
            PARTICLE_TYPES.register("soul_shoot", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SOUL_SHOOT_RED =
            PARTICLE_TYPES.register("soul_shoot_red", () -> new SimpleParticleType(true));



}
