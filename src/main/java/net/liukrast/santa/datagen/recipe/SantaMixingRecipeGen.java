package net.liukrast.santa.datagen.recipe;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaFluids;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SantaMixingRecipeGen extends MixingRecipeGen {
    public SantaMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, SantaConstants.MOD_ID);

        create("molten_sugar", b -> b
                .require(Items.SUGAR)
                .require(Items.RED_DYE)
                .output(SantaFluids.MOLTEN_SUGAR.get(), 250)
                .requiresHeat(HeatCondition.HEATED));

        create("cryolite", b -> b
                .require(SantaItems.CRYOLITE_POWDER)
                .require(Items.SNOW_BLOCK)
                .output(SantaFluids.CRYOLITE.get(), 1000)
                .requiresHeat(HeatCondition.HEATED));
    }
}
