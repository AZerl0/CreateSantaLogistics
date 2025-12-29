package net.liukrast.santa.world.item.crafting;

import net.liukrast.santa.registry.SantaRecipeSerializers;
import net.liukrast.santa.registry.SantaRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.lwjgl.system.NonnullDefault;

/**
 * @param food defines whether Santa Claus should get the eating animation on this trade or not
 * */
@NonnullDefault
public record SantaClausTradingRecipe(SizedIngredient input, int trustRequired, boolean food, ItemStack result) implements Recipe<SantaClausTradingRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public boolean matches(SantaClausTradingRecipeInput input, Level level) {
        return this.input.ingredient().test(input.input()) && input.trust() >= trustRequired;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public ItemStack assemble(SantaClausTradingRecipeInput santaClausTradingRecipeInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return SantaRecipeTypes.SANTA_CLAUS_TRADING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SantaRecipeSerializers.SANTA_CLAUS_TRADING.get();
    }
}
