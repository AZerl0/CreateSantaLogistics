package net.liukrast.santa.world.level.entity;

import net.liukrast.santa.SantaConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class SantaState extends SavedData {
    private boolean state = false;

    public SantaState() {
    }

    private SantaState(boolean state) {
        this.state = state;
    }

    public static void setState(ServerLevel level, boolean flag) {
        var storage = get(level);
        storage.state = flag;
        storage.setDirty();
    }

    public static boolean isSantaSpawned(ServerLevel level) {
        return get(level).state;
    }

    private static SantaState get(ServerLevel level) {
        return level.getDataStorage()
                .computeIfAbsent(new Factory<>(SantaState::new, SantaState::load), SantaConstants.MOD_ID + "_santa_state");
    }

    public static SantaState load(CompoundTag tag, HolderLookup.Provider registries) {
        return new SantaState(tag.getBoolean("State"));
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putBoolean("State", state);
        return compoundTag;
    }
}
