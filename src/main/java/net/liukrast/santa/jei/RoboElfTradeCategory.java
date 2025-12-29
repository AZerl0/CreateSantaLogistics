package net.liukrast.santa.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.lwjgl.system.NonnullDefault;

import java.util.Arrays;
import java.util.List;

@NonnullDefault
public class RoboElfTradeCategory extends AbstractRecipeCategory<RecipeHolder<RoboElfTradingRecipe>> {
    public RoboElfTradeCategory(IGuiHelper helper) {
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
                .addIngredients(VanillaTypes.ITEM_STACK, List.of(Arrays.stream(recipe
                        .inputA()
                        .getItems())
                        .map(stack -> {
                            ItemStack re = stack.copy();
                            re.setCount(recipe.inputA().count());
                            return re;
                        })
                        .toArray(ItemStack[]::new)
                ));

        if(recipe.inputB().isPresent())
            builder.addInputSlot(21, 19)
                    .setStandardSlotBackground()
                    .addIngredients(VanillaTypes.ITEM_STACK, List.of(Arrays.stream(recipe
                                    .inputB().get()
                                    .getItems())
                            .map(stack -> {
                                ItemStack re = stack.copy();
                                re.setCount(recipe.inputB().get().count());
                                return re;
                            })
                            .toArray(ItemStack[]::new)
                    ));

        if(recipe.inputC().isPresent())
            builder.addInputSlot(41, 19)
                    .setStandardSlotBackground()
                    .addIngredients(VanillaTypes.ITEM_STACK, List.of(Arrays.stream(recipe
                                    .inputC().get()
                                    .getItems())
                            .map(stack -> {
                                ItemStack re = stack.copy();
                                re.setCount(recipe.inputC().get().count());
                                return re;
                            })
                            .toArray(ItemStack[]::new)
                    ));


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
