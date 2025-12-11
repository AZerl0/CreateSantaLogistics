package net.liukrast.santa.datagen.recipe;

import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class SantaCrushingRecipeGen extends CrushingRecipeGen {
    GeneratedRecipe

    CRYOLITE_CLUSTER = create(() -> SantaBlocks.CRYOLITE_CLUSTER, b -> b.duration(150)
            .output(SantaItems.CRYOLITE_SHARD, 7)
            .output(.5f, SantaItems.CRYOLITE_SHARD)),
    CRYOLITE_BLOCK = create(() -> SantaBlocks.CRYOLITE_BLOCK, b -> b.duration(150)
            .output(SantaItems.CRYOLITE_SHARD, 3)
            .output(.5f, SantaItems.CRYOLITE_SHARD));

    public SantaCrushingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, SantaConstants.MOD_ID);
    }
}
