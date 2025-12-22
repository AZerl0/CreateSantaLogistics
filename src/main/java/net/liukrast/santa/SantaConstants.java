package net.liukrast.santa;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Stream;

public class SantaConstants {
    private SantaConstants() {}

    public static final String MOD_ID = "santa_logistics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String PROTOCOL_VERSION = "1.0.0";

    public static final int NIGHT_START = 13000;
    public static final int NIGHT_END = 23000;
    public static final int LEAVE_DURATION = 200;
    public static final int EXIT_LENGTH = 100;
    public static final int EXIT_HEIGTH = 200;

    protected static final CreateRegistrate REGISTRATE = SantaRegistrate.create(SantaConstants.MOD_ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    public static ResourceLocation id(String path, Object... args) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, String.format(path, args));
    }

    public static <T> DeferredRegister<T> createDeferred(Registry<T> registry) {
        return DeferredRegister.create(registry, MOD_ID);
    }

    public static <T> Stream<T> getElements(Registry<T> registry) {
        return getElementEntries(registry).map(Map.Entry::getValue);
    }

    public static <T> Stream<Map.Entry<String, T>> getElementEntries(Registry<T> registry) {
        return registry.entrySet().stream()
                .filter(t -> t.getKey().location().getNamespace().equals(MOD_ID))
                .map(e -> Map.entry(e.getKey().location().getPath(), e.getValue()));
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static boolean fluidCapabilityExtension(Block block) {
        return block == SantaBlocks.FROSTBURN_ENGINE.get();
    }

    public static <T> ResourceKey<T> registerKey(ResourceKey<? extends Registry<T>> registry, String name) {
        return ResourceKey.create(registry, id(name));
    }
}
