package net.liukrast.santa.world.item.crafting;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
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

@NonnullDefault
public class SantaClausTradingRecipeBuilder implements RecipeBuilder {
    protected final SizedIngredient input;
    protected final int trustRequired;
    protected final boolean food;
    protected final ItemStack result;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    public SantaClausTradingRecipeBuilder(SizedIngredient input, int trustRequired, boolean food, ItemStack result) {
        this.input = input;
        this.trustRequired = trustRequired;
        this.food = food;
        this.result = result;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public SantaClausTradingRecipeBuilder group(@Nullable String group) {
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
        SantaClausTradingRecipe recipe = new SantaClausTradingRecipe(input, trustRequired, food, result);
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
