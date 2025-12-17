package net.liukrast.santa.datagen.recipe;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class SantaMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {
    public SantaMechanicalCraftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, SantaConstants.MOD_ID);

        create(SantaBlocks.FROSTBURN_ENGINE::get).returns(1)
                .recipe(b -> b
                        .key('A', SantaItems.FROSTBURN_CORE)
                        .key('B', AllBlocks.FLUID_TANK)
                        .key('C', AllItems.STURDY_SHEET)
                        .key('D', AllBlocks.MECHANICAL_PUMP)
                        .patternLine(" D D ")
                        .patternLine("DCBCD")
                        .patternLine(" BAB ")
                        .patternLine("DCBCD")
                        .patternLine(" D D ")
                        .disallowMirrored()
                );
    }
}
