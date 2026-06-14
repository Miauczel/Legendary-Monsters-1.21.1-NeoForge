package net.miauczel.legendary_monsters.event;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.Particle.*;
import net.miauczel.legendary_monsters.Particle.custom.*;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = LegendaryMonsters.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticleFactiories {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BEAM.get(),
                LightningBeam.Provider::new);
       event.registerSpriteSet(ModParticles.TORNADO.get(),
                Tornado.Provider::new);
       event.registerSpriteSet(ModParticles.TORNADO_FIRE.get(),
                Tornado.Provider::new);
       event.registerSpriteSet(ModParticles.RING.get(),
                Circle.RingFactory::new);
       event.registerSpriteSet(ModParticles.LB.get(),
                LightningBolt.Factory::new);
       event.registerSpriteSet(ModParticles.FLAME.get(),
                Flame.Provider::new);
       event.registerSpriteSet(ModParticles.FLAME_STATIC.get(),
                Flame_Static.Provider::new);
       event.registerSpriteSet(ModParticles.CHORUS_SMOKE.get(),
                ChorusSmoke.Provider::new);

       event.registerSpriteSet(ModParticles.TELEPORT_EFFECT.get(),
                BlackHole.Factory::new);
       event.registerSpriteSet(ModParticles.SOULSTRIKE.get(),
                SoulStrike.Factory::new);

       event.registerSpriteSet(ModParticles.SOULSTRIKE_RED.get(),
                SoulStrike.Factory::new);
       event.registerSpriteSet(ModParticles.GHOSTLY_SOUL.get(),
                GhostlySoul.Provider::new);
       event.registerSpriteSet(ModParticles.GHOSTLY_SOUL_RED.get(),
                GhostlySoul.Provider::new);

       event.registerSpriteSet(ModParticles.DIMENSIONAL_BOMB_EXPLOSION.get(),
                DimensionalBombExplosion.Provider::new);
       event.registerSpriteSet(ModParticles.ANNIHILATION_FLAME_STRIKE.get(),
                AnnihilationFlame.Factory::new);
       event.registerSpriteSet(ModParticles.ANNIHILATION_SWEEP_PARTICLE.get(),
                AnnihilationSweepParticle.Provider::new);
       event.registerSpriteSet(ModParticles.SOUL_SWEEP.get(),
                SoulSweepParticle.Provider::new);
       event.registerSpriteSet(ModParticles.KNIGHTS_SWEEP.get(),
                KnightSweepParticle.Provider::new);
       event.registerSpriteSet(ModParticles.SOUL_SWEEP_RED.get(),
                SoulSweepRedParticle.Provider::new);
       event.registerSpriteSet(ModParticles.GIANT_ANNIHILATION_SWEEP_PARTICLE.get(),
                GiantAnnihilationSweepParticle.Provider::new);
       event.registerSpriteSet(ModParticles.BIG_ANNIHILATION_SWEEP.get(),
                BigAnnihilationSweepParticle.Provider::new);
       event.registerSpriteSet(ModParticles.BIG_ANNIHILATION_FLAME.get(),
                BigFlameParticle.Provider::new);
       event.registerSpriteSet(ModParticles.BIG_ORANGE_FLAME.get(),
                BigFlameParticle.Provider::new);
       event.registerSpriteSet(ModParticles.SMALL_ANNIHILATION_FLAME.get(),
                SmallGreenFlame.Provider::new);

       event.registerSpriteSet(ModParticles.RED_SOUL_FLAME.get(),
                SmallGreenFlame.Provider::new);
       event.registerSpriteSet(ModParticles.ANNIHILATION_EXPLOSION.get(),
                AnnihilationExplosion.Provider::new);
       event.registerSpriteSet(ModParticles.DIMENSIONAL_EXPLOSION.get(),
                DimensionalExplosion.Factory::new);
       event.registerSpriteSet(ModParticles.ANNIHILATION_GEYSER.get(),
                AnnihilationGeyser.Factory::new);
       event.registerSpriteSet(ModParticles.ANNIHILATION_NUKE.get(),
                AnnihilatedNuke.Factory::new);
       event.registerSpriteSet(ModParticles.LM_COSY_SMOKE.get(),
                ModCampfireSmoke.CosyProvider::new);

       event.registerSpriteSet(ModParticles.GROUND_ANNIHILATION_NUKE.get(),
                GroundAnnihilatedNuke.Factory::new);

       event.registerSpriteSet(ModParticles.SHULKER_EXPLOSION.get(),
                ShulkerExplosion.Provider::new);

       event.registerSpriteSet(ModParticles.PURPLE_SHULKER_EXPLOSION.get(),
                PurpleShulkerExplosion.Provider::new);
       event.registerSpriteSet(ModParticles.SHULKER_WIND.get(),
                ShulkerWindParticle.Provider::new);
        event.registerSpecial(ModParticles.POWER_BOMB_TRAIL.get(), new AnnihilationBombTrail.OrbFactory());

        event.registerSpecial(ModParticles.PHANTOM_DAGGER_TRAIL.get(), new PhantomDaggerTrail.OrbFactory());
        event.registerSpecial(ModParticles.AIR_TRAIL.get(), new AirTrailParticle.OrbFactory());

       event.registerSpriteSet(ModParticles.SHULKER_CONFETTI.get(),
                ShulkerConfettiParticle.Provider::new);
        event.registerSpecial(ModParticles.MOVING_TRAIL.get(), new MovingTrailParticle.OrbFactory());
        event.registerSpecial(ModParticles.LASER_BULLET_TRAIL.get(), new LaserBulletTrail.OrbFactory());
        event.registerSpecial(ModParticles.LIGHTNING.get(), new LightningParticle.OrbFactory());

       event.registerSpriteSet(ModParticles.GROUNDSOUL.get(),
                GroundSoulParticle.Factory::new);
       event.registerSpriteSet(ModParticles.SOUL_PILLAR_EXPLOSION.get(),
                SoulPillarExplosion.Factory::new);

       event.registerSpriteSet(ModParticles.BEHEADED_KNIGHT_SWEEP.get(),
                BeheadedKnightSweepParticle.Provider::new);


       event.registerSpriteSet(ModParticles.SOUL_EXPLOSION.get(),
                SoulExplosion.Provider::new);
       event.registerSpriteSet(ModParticles.SOUL_EXPLOSION_RED.get(),
                SoulExplosion.Provider::new);


       event.registerSpriteSet(ModParticles.GROUNDSOUL_RED.get(),
                GroundSoulParticle.Factory::new);


       event.registerSpriteSet(ModParticles.SOUL_SIGIL.get(),
                SoulSigil.RingFactory::new);

       event.registerSpriteSet(ModParticles.SOUL_SHOOT.get(),
                SoulShoot.Provider::new);

       event.registerSpriteSet(ModParticles.SOUL_SHOOT_RED.get(),
                SoulShoot.Provider::new);

       event.registerSpriteSet(ModParticles.SMALL_SOUL_FIRE_FLAME.get(),
                SmallGreenFlame.SmallFlameProvider::new);


    }
}
