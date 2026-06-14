package net.miauczel.legendary_monsters.sound;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;


public class ModSounds extends SoundEvents {
    public static final net.neoforged.neoforge.registries.DeferredRegister<SoundEvent> SOUND_EVENTS =
            net.neoforged.neoforge.registries.DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, LegendaryMonsters.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ENDERSENT_ATTACK = registerSoundEvent("endersent_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENDERSENT_AMBIENT = registerSoundEvent("endersent_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENDERSENT_DEATH = registerSoundEvent("endersent_death");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENDERSENT_HURT1 = registerSoundEvent("endersent_hurt1");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENDERSENT_HURT2 = registerSoundEvent("endersent_hurt2");

    public static final DeferredHolder<SoundEvent, SoundEvent> OVERGROWN_COLOSSUS_HURT = registerSoundEvent("overgrown_colossus_hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> OVERGROWN_COLOSSUS_DEATH = registerSoundEvent("overgrown_colossus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKELETOSAURUS_HURT1 = registerSoundEvent("skeletosaurus_hurt1");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKELETOSAURUS_HURT2 = registerSoundEvent("skeletosaurus_hurt2");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKELETOSAURUS_HURT3 = registerSoundEvent("skeletosaurus_hurt3");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKELETOSAURUS_DEATH = registerSoundEvent("skeletosaurus_death");

    public static final DeferredHolder<SoundEvent, SoundEvent> CHORUSLING_DEATH = registerSoundEvent("chorusling_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHORUSLING_HURT = registerSoundEvent("chorusling_hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> CHORUSLING_AMBIENT = registerSoundEvent("chorusling_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHORUSLING_ATTACK = registerSoundEvent("chorusling_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> WITHERED_ABOMINATION_HURT = registerSoundEvent("withered_abomination_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> WITHERED_ABOMINATION_DEATH = registerSoundEvent("withered_abomination_death");

    public static final DeferredHolder<SoundEvent, SoundEvent> ANCIENT_GUARDIAN_HURT = registerSoundEvent("ancient_guardian_hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ANCIENT_GUARDIAN_DEATH = registerSoundEvent("ancient_guardian_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIVING_ARMOR_HURT = registerSoundEvent("living_armor_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> WEAPON_SPIN = registerSoundEvent("posessed_paladin_spin");
    public static final DeferredHolder<SoundEvent, SoundEvent> GENERIC_ARM_SWING = registerSoundEvent("posessed_paladin_swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> POSESSED_PALADIN_ATTACK = registerSoundEvent("posessed_paladin_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> POSESSED_PALADIN_ATTACK2 = registerSoundEvent("posessed_paladin_attack2");
    public static final DeferredHolder<SoundEvent, SoundEvent> POSESSED_PALADIN_ATTACK3 = registerSoundEvent("posessed_paladin_attack3");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOSSY_GOLEM_SWING = registerSoundEvent("mossy_golem_swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOSSY_GOLEM_ATTACK = registerSoundEvent("mossy_golem_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> STEP_SOUND = registerSoundEvent("step_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> STEP_SOUND2 = registerSoundEvent("step_sound_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> STEP_SOUND3 = registerSoundEvent("step_sound_3");
    public static final DeferredHolder<SoundEvent, SoundEvent> STEP_SOUND4 = registerSoundEvent("step_sound_4");
    public static final DeferredHolder<SoundEvent, SoundEvent> CANNON_SHOOT_1 = registerSoundEvent("cannon_shoot_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> CANNON_SHOOT_2 = registerSoundEvent("cannon_shoot_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> CANNON_SHOOT_3 = registerSoundEvent("cannon_shoot_3");
    public static final DeferredHolder<SoundEvent, SoundEvent> FATAL_MORTAR_ATTACK = registerSoundEvent("fatal_mortar_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> DUNE_SENTINEL_HURT = registerSoundEvent("dune_sentinel_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DUNE_SENTINEL_DEATH = registerSoundEvent("dune_sentinel_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> WITHERED_ABOMINATION_DASH = registerSoundEvent("withered_abomination_dash");
    public static final DeferredHolder<SoundEvent, SoundEvent> WITHERED_ABOMINATION_DASH2 = registerSoundEvent("withered_abomination_dash2");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCHOR_SLAM = registerSoundEvent("anchor_slam");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCHOR_SWING1 = registerSoundEvent("anchor_swing1");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCHOR_SWING2 = registerSoundEvent("anchor_swing2");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCHOR_HIT = registerSoundEvent("anchor_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> ICE_SPIKE_EMERGE = registerSoundEvent("ice_spike_emerge");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCIENT_GUARDIAN_ROAR = registerSoundEvent("ancient_guardian_roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> POSSESSED_PALADIN_SWING = registerSoundEvent("pp_swing");

    public static final DeferredHolder<SoundEvent, SoundEvent> BEAM_CHARGE = registerSoundEvent("beam_charge");

    public static final DeferredHolder<SoundEvent, SoundEvent> BEAM_GO = registerSoundEvent("beam_go");

    public static final DeferredHolder<SoundEvent, SoundEvent> CGH = registerSoundEvent("cloud_golem_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> BEAM_LOOP = registerSoundEvent("beam_loop");

    public static final DeferredHolder<SoundEvent, SoundEvent> CGA = registerSoundEvent("cloud_golem_ambient");

    public static final DeferredHolder<SoundEvent, SoundEvent> CGD = registerSoundEvent("cloud_golem_death");

    public static final DeferredHolder<SoundEvent, SoundEvent> LS = registerSoundEvent("lightning_strike");

    public static final DeferredHolder<SoundEvent, SoundEvent> GROUND_IMPACT = registerSoundEvent("ground_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENDERSENT_TP_HIT = registerSoundEvent("endersent_tp_hit");

    public static final DeferredHolder<SoundEvent, SoundEvent> CLOUD_GOLEM_MUSIC = registerSoundEvent("cloud_golem_music");
    public static final ResourceKey<JukeboxSong> CLOUD_GOLEM_MUSIC_KEY = createSong("cloud_golem_music");

    public static final DeferredHolder<SoundEvent, SoundEvent> CLOUD_GOLEM_MUSIC_STEREO = registerSoundEvent("cloud_golem_music_stereo");
    public static final DeferredHolder<SoundEvent, SoundEvent> THE_WARPED_ONE_ROAR = registerSoundEvent("the_warped_one_roar");

    public static final DeferredHolder<SoundEvent, SoundEvent> POWERFUL_SWORD_IMPACT = registerSoundEvent("powerful_sword_impact");

    public static final DeferredHolder<SoundEvent, SoundEvent> POWERFUL_SWORD_IMPACT2 = registerSoundEvent("powerful_sword_impact2");

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUL_FLY = registerSoundEvent("soul_fly");

    public static final DeferredHolder<SoundEvent, SoundEvent> WEAPON_IMPACT = registerSoundEvent("weapon_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> THE_WARPED_ONE_SHOOT = registerSoundEvent("the_warped_one_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> THE_WARPED_ONE_HURT = registerSoundEvent("the_warped_one_hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> DIMENSIONAL_BOMB_EXPLODE = registerSoundEvent("dimensional_bomb_explode");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIMENSIONAL_BOMB_EXPLODE_SMALL = registerSoundEvent("dimensional_bomb_explosion2");

    public static final DeferredHolder<SoundEvent, SoundEvent> DIMENSIONAL_SHOOT_CHARGE = registerSoundEvent("dimensional_shoot_charge");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENERGY_EXPLOSION = registerSoundEvent("energy_explosion");

    public static final DeferredHolder<SoundEvent, SoundEvent> HEAVY_SWING = registerSoundEvent("heavy_swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> THE_OBLITERATOR_STUN = registerSoundEvent("the_warped_one_stun");

    public static final DeferredHolder<SoundEvent, SoundEvent> HUGE_ENERGY_EXPLOSION = registerSoundEvent("huge_energy_explosion");

    public static final DeferredHolder<SoundEvent, SoundEvent> THE_WARPED_ONE_HURT2 = registerSoundEvent("the_warped_one_hurt2");

    public static final DeferredHolder<SoundEvent, SoundEvent> ANNIHILATION_LASER_SINGLE_SHOOT = registerSoundEvent("single_annihilation_laser_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ANNIHILATION_LASER_CHARGE = registerSoundEvent("annihilation_laser_charge");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_ANNIHILATION_LASER_SHOOT = registerSoundEvent("quad_laser_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> OBLITERATOR_ARM_SHOOT = registerSoundEvent("obliterator_arm_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> OMINOUS_EXPLOSION = registerSoundEvent("ominous_explosion");

    public static final DeferredHolder<SoundEvent, SoundEvent> FLAME_BURST = registerSoundEvent("flame_burst");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK = registerSoundEvent("block");

    public static final DeferredHolder<SoundEvent, SoundEvent> ULTIMATE_FLAME_IMPACT = registerSoundEvent("ultimate_flame_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> ULTIMATE_FLAME_BIG_IMPACT = registerSoundEvent("ultimate_flame_big_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> FLAME_DRIFTER_CHARGE_SHOOT = registerSoundEvent("flame_drifter_charge_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> THE_OBLITERATOR_SHORT_ROAR = registerSoundEvent("the_obliterator_short_roar");

    public static final DeferredHolder<SoundEvent, SoundEvent> STAB_HIT = registerSoundEvent("stab_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> POSSESSED_PALADIN_STAB = registerSoundEvent("pp_stab");

    public static final DeferredHolder<SoundEvent, SoundEvent> HEAVY_STAB = registerSoundEvent("heavy_stab");

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUL_SHIELD_SMASH = registerSoundEvent("soul_shield_smash");

    public static final DeferredHolder<SoundEvent, SoundEvent> OBLITERATOR_STEREO = registerSoundEvent("obliterator_stereo");
    public static final ResourceKey<JukeboxSong> OBLITERATOR_MONO_KEY = createSong("obliterator_mono");

    public static final DeferredHolder<SoundEvent, SoundEvent> OBLITERATOR_MONO = registerSoundEvent("obliterator_mono");

    public static final DeferredHolder<SoundEvent, SoundEvent> DAGGER_THROW = registerSoundEvent("dagger_throw");

    public static final DeferredHolder<SoundEvent, SoundEvent> POSSESSED_PALADIN_ROLL = registerSoundEvent("possessed_paladin_roll");

    public static final DeferredHolder<SoundEvent, SoundEvent> OMINOUS_WIND_UP = registerSoundEvent("ominous_windup");

    public static final DeferredHolder<SoundEvent, SoundEvent> SPEAR_STAB = registerSoundEvent("spear_stab");

    public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_EYE_HURT = registerSoundEvent("wandering_eye_hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_EYE_AMBIENT = registerSoundEvent("wandering_eye_ambient");

    public static final ResourceKey<JukeboxSong> POSSESSED_PALADIN_MONO_KEY = createSong("possessed_paladin_mono");

    public static final DeferredHolder<SoundEvent, SoundEvent> POSSESSED_PALADIN_MONO = registerSoundEvent("possessed_paladin_mono");

    public static final DeferredHolder<SoundEvent, SoundEvent> POSSESSED_PALADIN_STEREO = registerSoundEvent("possessed_paladin_stereo");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, name)));
    }

    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, name));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}

