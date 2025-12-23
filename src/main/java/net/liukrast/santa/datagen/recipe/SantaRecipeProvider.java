package net.liukrast.santa.datagen.recipe;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.lwjgl.system.NonnullDefault;

import java.util.concurrent.CompletableFuture;

@NonnullDefault
public class SantaRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public SantaRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, SantaBlocks.SANTA_DOCK)
                .pattern("ABC").pattern("DED")
                .define('A', SantaBlocks.CHRISTMAS_TREE)
                .define('B', AllBlocks.DISPLAY_BOARD)
                .define('C', AllItems.ELECTRON_TUBE)
                .define('D', itemTag("c:plates/iron"))
                .define('E', AllBlocks.ANDESITE_CASING)
                .unlockedBy("has_christmas_tree", has(SantaBlocks.CHRISTMAS_TREE)).save(recipeOutput);
        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, SantaBlocks.CRYOLITE_BLOCK, SantaItems.CRYOLITE_SHARD);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, SantaBlocks.ELF_CHARGE_STATION)
                .pattern("A").pattern("B").pattern("C")
                .define('A', AllBlocks.SHAFT)
                .define('B', AllBlocks.BRASS_CASING)
                .define('C', SantaItems.CRYOLITE_SHARD)
                .unlockedBy("has_cryolite_shard", has(SantaItems.CRYOLITE_SHARD)).save(recipeOutput);
    }

    private static TagKey<Item> itemTag(@SuppressWarnings("SameParameterValue") String value) {
        return itemTag(ResourceLocation.parse(value));
    }

    private static TagKey<Item> itemTag(ResourceLocation value) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), value);
    }
}
