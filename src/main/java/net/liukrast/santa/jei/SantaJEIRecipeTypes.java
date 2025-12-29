package net.liukrast.santa.jei;

import mezz.jei.api.recipe.RecipeType;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class SantaJEIRecipeTypes {
    private SantaJEIRecipeTypes() {}

    public static final RecipeType<RecipeHolder<RoboElfTradingRecipe>> ROBO_ELF_TRADING =
            RecipeType.createRecipeHolderType(SantaConstants.id("robo_elf_trading"));
}
