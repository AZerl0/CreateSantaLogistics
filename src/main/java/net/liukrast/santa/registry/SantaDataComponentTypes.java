package net.liukrast.santa.registry;

import net.liukrast.santa.SantaConstants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SantaDataComponentTypes {
    private SantaDataComponentTypes() {}
    private static final DeferredRegister<DataComponentType<?>> REGISTER = SantaConstants.createDeferred(BuiltInRegistries.DATA_COMPONENT_TYPE);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> HIDDEN_CONTENTS = REGISTER.register("hidden_contents", () -> DataComponentType.<ItemContainerContents>builder()
            .persistent(ItemContainerContents.CODEC)
            .build());

    public static void init(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
