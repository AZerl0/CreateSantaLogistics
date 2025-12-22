package net.liukrast.santa.datagen;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class SantaPonderTagProvider {
    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        var HELPER = helper.withKeyFunction(BuiltInRegistries.ITEM::getKey);

        HELPER.addToTag(AllCreatePonderTags.HIGH_LOGISTICS)
                .add(SantaBlocks.SANTA_DOCK.asItem());

        HELPER.addToTag(AllCreatePonderTags.KINETIC_SOURCES)
                .add(SantaBlocks.FROSTBURN_ENGINE.asItem());
    }
}
