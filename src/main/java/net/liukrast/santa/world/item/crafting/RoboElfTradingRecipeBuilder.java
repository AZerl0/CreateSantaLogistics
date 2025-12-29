package net.liukrast.santa.world.item.crafting;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@NonnullDefault
public class RoboElfTradingRecipeBuilder implements RecipeBuilder {
    protected final SizedIngredient inputA;
    @Nullable
    protected SizedIngredient inputB = null;
    @Nullable
    protected SizedIngredient inputC = null;
    protected final int trustGain;
    protected final int energy;
    protected final int processTime;
    protected final ItemStack result;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    public static RoboElfTradingRecipeBuilder full(SizedIngredient inputA, SizedIngredient inputB, SizedIngredient inputC, int trustGain, int energy, int processTime, ItemStack result) {
        return trade(inputA, trustGain, energy, processTime, result).add(inputB).add(inputC);
    }

    public static RoboElfTradingRecipeBuilder trade(SizedIngredient input, int trustGain, int energy, int processTime, ItemStack result) {
        return new RoboElfTradingRecipeBuilder(input, trustGain, energy, processTime, result);
    }

    private RoboElfTradingRecipeBuilder(SizedIngredient input, int trustGain, int energy, int processTime, ItemStack result) {
        this.result = result;
        this.inputA = input;
        this.trustGain = trustGain;
        this.energy = energy;
        this.processTime = processTime;
    }

    public RoboElfTradingRecipeBuilder add(SizedIngredient input) {
        if(inputB == null) {
            inputB = input;
            return this;
        }
        if(inputC == null) {
            inputC = input;
            return this;
        }
        throw new IllegalCallerException("You cannot define more than 3 inputs for trade");
    }

    @Override
    public RoboElfTradingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RoboElfTradingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        RoboElfTradingRecipe recipe = new RoboElfTradingRecipe(inputA, Optional.ofNullable(inputB), Optional.ofNullable(inputC), trustGain, energy, processTime, result);
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
