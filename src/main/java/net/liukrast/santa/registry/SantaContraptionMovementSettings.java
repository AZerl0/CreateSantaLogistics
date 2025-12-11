package net.liukrast.santa.registry;

import com.simibubi.create.api.contraption.ContraptionMovementSetting;

public class SantaContraptionMovementSettings {
    private SantaContraptionMovementSettings() {}

    public static void init() {
        ContraptionMovementSetting.REGISTRY.register(SantaBlocks.SANTA_DOOR.get(), () -> ContraptionMovementSetting.UNMOVABLE);
        ContraptionMovementSetting.REGISTRY.register(SantaBlocks.BUDDING_CRYOLITE.get(), () -> ContraptionMovementSetting.UNMOVABLE);
    }
}
