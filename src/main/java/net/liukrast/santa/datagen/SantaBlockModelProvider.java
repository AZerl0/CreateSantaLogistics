package net.liukrast.santa.datagen;

import net.liukrast.santa.SantaConstants;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SantaBlockModelProvider  extends BlockModelProvider {
    public SantaBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SantaConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        
    }
}
