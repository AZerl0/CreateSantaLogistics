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

import java.util.Optional;

@NonnullDefault
public record RoboElfTradingRecipe(SizedIngredient inputA, Optional<SizedIngredient> inputB, Optional<SizedIngredient> inputC, int trustGain, int energy, int processTime, ItemStack result) implements Recipe<RoboElfTradingRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public boolean matches(RoboElfTradingRecipeInput input, Level level) {
        if(!inputA.test(input.a())) return false;
        if(inputB.isEmpty()) return true;
        if(!inputB.get().test(input.b())) return false;
        return inputC
                .map(sizedIngredient -> sizedIngredient.test(input.c()))
                .orElse(true);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public ItemStack assemble(RoboElfTradingRecipeInput roboElfTradeRecipeInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return SantaRecipeTypes.ROBO_ELF_TRADING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SantaRecipeSerializers.ROBO_ELF_TRADING.get();
    }

    public SizedIngredient[] getInputs() {
        return inputB.map(ingredient -> inputC
                        .map(sizedIngredient -> new SizedIngredient[]{inputA, ingredient, sizedIngredient})
                        .orElseGet(() -> new SizedIngredient[]{inputA, ingredient}))
                .orElseGet(() -> new SizedIngredient[]{inputA});
    }
}
