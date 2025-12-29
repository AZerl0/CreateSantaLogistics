package net.liukrast.santa.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class RoboElfTradingCategory extends AbstractRecipeCategory<RecipeHolder<RoboElfTradingRecipe>> {
    public RoboElfTradingCategory(IGuiHelper helper) {
        super(
                SantaJEIRecipeTypes.ROBO_ELF_TRADING,
                Component.translatable("santa_logistics.recipe.robo_elf_trading"),
                helper.createDrawableItemLike(SantaItems.ROBO_ELF_SPAWN_EGG.get()), //TODO: Replace with 3D robo elf
                128, 54
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<RoboElfTradingRecipe> holder, IFocusGroup focuses) {
        var recipe = holder.value();
        builder.addInputSlot(1, 19)
                .setStandardSlotBackground()
                .addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe.inputA().getItems()));
        if(recipe.inputB().isPresent())
            builder.addInputSlot(21, 19)
                    .setStandardSlotBackground()
                    .addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe.inputB().get().getItems()));
        if(recipe.inputC().isPresent())
            builder.addInputSlot(41, 19)
                    .setStandardSlotBackground()
                    .addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe.inputC().get().getItems()));
        builder.addOutputSlot(100, 19)
                .setOutputSlotBackground()
                .addItemStack(recipe.result());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<RoboElfTradingRecipe> recipe, IFocusGroup focuses) {
        var value = recipe.value();

        builder.addRecipeArrow()
                .setPosition(61, 17);

        builder.addText(Component.literal("+" + value.trustGain() + "☺  -" + value.energy() + "⚡  " + value.processTime() + "⌚"), getWidth() - 20, 10)
                .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
                .setTextAlignment(HorizontalAlignment.RIGHT)
                .setTextAlignment(VerticalAlignment.BOTTOM)
                .setColor(0xFF808080);
    }
}
