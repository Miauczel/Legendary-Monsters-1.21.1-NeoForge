package net.miauczel.legendary_monsters.sound;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface ModJukeBoxSongs {
    ResourceKey<JukeboxSong> PALADIN = create("paladin");
    ResourceKey<JukeboxSong> OBLITERATOR = create("obliterator");
    ResourceKey<JukeboxSong> CLOUD_GOLEM = create("cloud_golem");

    private static ResourceKey<JukeboxSong> create(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, name));
    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, DeferredHolder<SoundEvent, SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), (float) lengthInSeconds, comparatorOutput));
    }

    static void bootstrap(BootstrapContext<JukeboxSong> context) {
      //  register(context, CLOUD_GOLEM, ModSounds.CLOUD_GOLEM_MUSIC, 178, 1);
      //  register(context, OBLITERATOR, ModSounds.OBLITERATOR_MONO, 185, 2);
      //  register(context, PALADIN, ModSounds.POSSESSED_PALADIN_MONO, 185, 2);

    }
}
