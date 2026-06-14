package net.miauczel.legendary_monsters.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class ModSavedData extends SavedData {
    public static final String DATA_NAME = "legendary_monsters_worlddata";

    private boolean defeatedObliterator;

    public ModSavedData() {
    }

    public static ModSavedData load(CompoundTag tag, HolderLookup.Provider provider) {
        ModSavedData data = new ModSavedData();

        data.defeatedObliterator = tag.getBoolean("defeatedObliterator");

        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putBoolean("defeatedObliterator", this.defeatedObliterator);
        return tag;
    }

    private static final SavedData.Factory<ModSavedData> FACTORY =
            new SavedData.Factory<>(ModSavedData::new, ModSavedData::load);

    public static ModSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, DATA_NAME);
    }

    public boolean isDefeatedObliterator() {
        return defeatedObliterator;
    }

    public void setDefeatedObliterator(boolean value) {
        if (this.defeatedObliterator != value) {
            this.defeatedObliterator = value;
            this.setDirty();
        }
    }
}