package net.liukrast.santa.world.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public record SantaClausTradingRecipeInput(ItemStack input, int trust) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        if(i == 0) return input;
        throw new IllegalArgumentException("No item for index " + i);
    }

    @Override
    public int size() {
        return 1;
    }
}
