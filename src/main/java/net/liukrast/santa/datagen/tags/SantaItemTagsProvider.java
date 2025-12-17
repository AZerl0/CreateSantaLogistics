package net.liukrast.santa.datagen.tags;

import com.simibubi.create.AllFluids;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.registry.SantaTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.concurrent.CompletableFuture;

@NonnullDefault
public class SantaItemTagsProvider extends ItemTagsProvider {

    public SantaItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SantaConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.GEMS).add(SantaItems.CRYOLITE_SHARD.get());

        tag(SantaTags.Items.SANTA_FOOD_A).add(Items.COOKIE).add(Items.MILK_BUCKET).add(AllFluids.CHOCOLATE.getBucket().orElseThrow());
        tag(SantaTags.Items.SANTA_FOOD_B).add(SantaItems.CANDY_CANE.get());
    }
}
