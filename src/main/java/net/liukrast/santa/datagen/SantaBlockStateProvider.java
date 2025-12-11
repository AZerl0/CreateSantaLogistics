package net.liukrast.santa.datagen;

import net.liukrast.multipart.datagen.MultiPartAPIStateHelper;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SantaBlockStateProvider extends BlockStateProvider {
    public SantaBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SantaConstants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(SantaBlocks.ELF_CHARGE_STATION.get());
        MultiPartAPIStateHelper.multiPartBlock(this, SantaBlocks.CHRISTMAS_TREE.get());
    }

    private void horizontalBlock(Block block) {
        horizontalBlock(block, new ModelFile.ExistingModelFile(SantaConstants.id("block/%s", BuiltInRegistries.BLOCK.getKey(block).getPath()), this.models().existingFileHelper));
    }
}
