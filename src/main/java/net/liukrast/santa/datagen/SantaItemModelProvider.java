package net.liukrast.santa.datagen;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.world.item.PresentItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class SantaItemModelProvider extends ItemModelProvider {
    public SantaItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SantaConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(SantaBlocks.SANTA_DOOR.asItem());
        simpleBlockItem(SantaBlocks.SANTA_DOCK.get());
        simpleBlockItem(SantaBlocks.ELF_CHARGE_STATION.get());
        SantaConstants.getElements(BuiltInRegistries.ITEM).filter(i -> i instanceof SpawnEggItem).forEach(this::spawnEggItem);
        for(DeferredItem<PresentItem> box : SantaItems.PRESENTS) {
            getBuilder(box.getId().toString())
                    .parent(new ModelFile.ExistingModelFile(SantaConstants.id("item/template_present"), existingFileHelper))
                    .texture("texture", SantaConstants.id("item/" + box.getId().getPath()));
        }
        basicItem(SantaBlocks.CHRISTMAS_TREE.asItem());
    }
}
