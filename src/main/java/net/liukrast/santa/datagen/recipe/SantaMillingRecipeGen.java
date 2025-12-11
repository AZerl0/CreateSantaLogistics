package net.liukrast.santa.datagen.recipe;

import com.simibubi.create.api.data.recipe.MillingRecipeGen;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class SantaMillingRecipeGen extends MillingRecipeGen {
    public SantaMillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, SantaConstants.MOD_ID);
        create(() -> SantaItems.CRYOLITE_SHARD, b -> b.duration(250)
                .output(SantaItems.CRYOLITE_POWDER));
    }
}
