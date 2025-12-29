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
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.lwjgl.system.NonnullDefault;

import java.util.stream.Stream;

@NonnullDefault
public class SantaClausTradingCategory extends AbstractRecipeCategory<RecipeHolder<SantaClausTradingRecipe>> {
    public SantaClausTradingCategory(IGuiHelper helper) {
        super(
                SantaJEIRecipeTypes.SANTA_CLAUS_TRADING,
                Component.translatable("santa_logistics.recipe.santa_claus_trading"),
                helper.createDrawableItemLike(SantaItems.SANTA_CLAUS_SPAWN_EGG.get()), //TODO: Replace with 3D robo elf
                128, 54
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<SantaClausTradingRecipe> holder, IFocusGroup focuses) {
        var recipe = holder.value();

        builder.addInputSlot(1, 19)
                .setStandardSlotBackground()
                .addIngredients(VanillaTypes.ITEM_STACK, Stream.of(recipe.input().getItems())
                        .map(stack -> stack.copyWithCount(1))
                        .toList());

        builder.addOutputSlot(100, 19)
                .setOutputSlotBackground()
                .addItemStack(recipe.result());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<SantaClausTradingRecipe> recipe, IFocusGroup focuses) {
        var value = recipe.value();

        builder.addRecipeArrow()
                .setPosition(61, 17);

        builder.addText(Component.literal("x" + recipe.value().input().count()), getWidth() - 20, 10)
                .setPosition(0, -20, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
                .setColor(0xFF808080);

        builder.addText(Component.literal(value.trustRequired() + "☺"), getWidth() - 20, 10)
                .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
                .setTextAlignment(HorizontalAlignment.RIGHT)
                .setTextAlignment(VerticalAlignment.BOTTOM)
                .setColor(0xFF808080);
    }
}
