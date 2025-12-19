package net.liukrast.santa.datagen.worldgen;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaEntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class SantaBiomeModifiers {

    public static final ResourceKey<BiomeModifier> SPAWN_ELF = registerKey("spawn_robo_elf");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
        context.register(SPAWN_ELF, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_SNOWY),
                List.of(new MobSpawnSettings.SpawnerData(SantaEntityTypes.ROBO_ELF.get(), 20, 2, 4))
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return SantaConstants.registerKey(NeoForgeRegistries.Keys.BIOME_MODIFIERS, name);
    }
}
