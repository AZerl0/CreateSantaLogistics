package net.liukrast.santa.world.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public record RoboElfTradingRecipeInput(ItemStack a, ItemStack b, ItemStack c, int trustGain, int energy, int processTime) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return switch (i) {
            case 0 -> a;
            case 1 -> b;
            case 2 -> c;
            default -> throw new IllegalArgumentException("No item for index " + i);
        };
    }

    @Override
    public int size() {
        return 3;
    }
}
