package net.liukrast.santa.datagen.recipe;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaFluids;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class SantaCompactingRecipeGen extends CompactingRecipeGen {
    public SantaCompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, SantaConstants.MOD_ID);

        create("candy_cane", b -> b.require(SantaFluids.MOLTEN_SUGAR.get(), 250).output(SantaItems.CANDY_CANE.get(), 1));
    }
}
