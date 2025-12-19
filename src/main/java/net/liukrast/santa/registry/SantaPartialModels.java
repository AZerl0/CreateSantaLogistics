package net.liukrast.santa.registry;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.liukrast.santa.SantaConstants;
import net.minecraft.resources.ResourceLocation;

public class SantaPartialModels {
    private SantaPartialModels() {}

    public static final PartialModel FROSTBURN_CORE = PartialModel.of(SantaConstants.id("block/frostburn_engine/core"));
    public static final PartialModel FROSTBURN_CORE_RING = PartialModel.of(SantaConstants.id("block/frostburn_engine/core_ring"));

    static {
        for(PackageStyles.PackageStyle style : SantaPackages.PRESENTS) {
            ResourceLocation key = SantaConstants.id(style.type() + "_present");
            PartialModel model = PartialModel.of(SantaConstants.id("item/" + key.getPath()));
            AllPartialModels.PACKAGES.put(key, model);
            if (!style.rare())
                AllPartialModels.PACKAGES_TO_HIDE_AS.add(model);
            AllPartialModels.PACKAGE_RIGGING.put(key, PartialModel.of(style.getRiggingModel()));
        }
    }

    public static void init() {}
}
