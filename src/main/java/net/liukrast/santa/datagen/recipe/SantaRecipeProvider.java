package net.liukrast.santa.datagen.recipe;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipeBuilder;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
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

        RoboElfTradingRecipeBuilder.trade(SizedIngredient.of(Items.SNOWBALL, 4), 10, 10, 10, Items.SNOW_BLOCK.getDefaultInstance())
                .unlockedBy("has_snowball", has(Items.SNOWBALL))
                .save(recipeOutput, SantaConstants.id("robo_elf/snow_block"));
        RoboElfTradingRecipeBuilder.trade(SizedIngredient.of(Items.SPRUCE_LOG, 4), 100, 40, 40, SantaBlocks.CHRISTMAS_TREE.toStack())
                .unlockedBy("has_spruce_log", has(Items.SPRUCE_LOG))
                .save(recipeOutput, SantaConstants.id("robo_elf/christmas_tree"));

        RoboElfTradingRecipeBuilder.trade(SizedIngredient.of(Items.SUGAR, 1), 20, 20, 10, SantaItems.CANDY_CANE.toStack())
                .add(SizedIngredient.of(Items.RED_DYE, 1))
                .unlockedBy("has_sugar", has(Items.SUGAR))
                .save(recipeOutput, SantaConstants.id("robo_elf/candy_cane"));

        RoboElfTradingRecipeBuilder.trade(SizedIngredient.of(Items.WHEAT, 2), 10, 10, 10, new ItemStack(Items.COOKIE, 8))
                .add(SizedIngredient.of(Items.COCOA_BEANS, 1))
                .unlockedBy("has_wheat", has(Items.WHEAT))
                .save(recipeOutput, SantaConstants.id("robo_elf/cookie"));

        RoboElfTradingRecipeBuilder.full(SizedIngredient.of(Items.SUGAR, 4), SizedIngredient.of(Items.COCOA_BEANS, 4), SizedIngredient.of(Items.MILK_BUCKET, 1), 40, 80, 60, AllFluids.CHOCOLATE.getBucket().orElseThrow().getDefaultInstance())
                .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
                .save(recipeOutput, SantaConstants.id("robo_elf/chocolate_bucket"));

        RoboElfTradingRecipeBuilder.trade(SizedIngredient.of(SantaBlocks.BUDDING_CRYOLITE.asItem(), 1), 10, 100, 200, new ItemStack(SantaBlocks.BUDDING_CRYOLITE, 2))
                .add(SizedIngredient.of(SantaItems.CRYOLITE_SHARD.asItem(), 1))
                .unlockedBy("has_budding_cryolite", has(SantaBlocks.BUDDING_CRYOLITE))
                .save(recipeOutput, SantaConstants.id("robo_elf/budding_cryolite"));
    }

    private static TagKey<Item> itemTag(@SuppressWarnings("SameParameterValue") String value) {
        return itemTag(ResourceLocation.parse(value));
    }

    private static TagKey<Item> itemTag(ResourceLocation value) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), value);
    }
}
