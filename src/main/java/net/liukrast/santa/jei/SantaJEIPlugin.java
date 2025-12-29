package net.liukrast.santa.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaRecipeTypes;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipe;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

@SuppressWarnings("unused")
@NonnullDefault
@JeiPlugin
public class SantaJEIPlugin implements IModPlugin {
    @SuppressWarnings("FieldCanBeLocal")
    @Nullable
    private IRecipeCategory<RecipeHolder<RoboElfTradingRecipe>> roboElfTradingCategory;
    @SuppressWarnings("FieldCanBeLocal")
    @Nullable
    private IRecipeCategory<RecipeHolder<SantaClausTradingRecipe>> santaClausTradingCategory;

    @Override
    public ResourceLocation getPluginUid() {
        return SantaConstants.id("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(roboElfTradingCategory = new RoboElfTradingCategory(guiHelper));
        registration.addRecipeCategories(santaClausTradingCategory = new SantaClausTradingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IIngredientManager ingredientManager = registration.getIngredientManager();
        IVanillaRecipeFactory vanillaRecipeFactory = registration.getVanillaRecipeFactory();
        Minecraft minecraft = Minecraft.getInstance();
        assert minecraft.level != null;
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        registration.addRecipes(SantaJEIRecipeTypes.ROBO_ELF_TRADING, minecraft.level.getRecipeManager().getAllRecipesFor(SantaRecipeTypes.ROBO_ELF_TRADING.get()));
        registration.addRecipes(SantaJEIRecipeTypes.SANTA_CLAUS_TRADING, minecraft.level.getRecipeManager().getAllRecipesFor(SantaRecipeTypes.SANTA_CLAUS_TRADING.get()));
    }
}
