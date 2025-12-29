package net.liukrast.santa.world.inventory;

import net.liukrast.santa.registry.SantaMenuTypes;
import net.liukrast.santa.registry.SantaRecipeTypes;
import net.liukrast.santa.world.entity.RoboElf;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class RoboElfMenu extends AbstractContainerMenu {
    public static final int TRADES_PER_PAGE = 4;
    public final RoboElf entity;
    private final List<RecipeHolder<RoboElfTradingRecipe>> recipes;

    public RoboElfMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buf) {
        this(containerId, playerInventory, (RoboElf) playerInventory.player.level().getEntity(buf.readInt()));
    }

    public RoboElfMenu(int containerId, Inventory playerInventory, RoboElf entity) {
        super(SantaMenuTypes.ROBO_ELF_MENU.get(), containerId);
        this.recipes = entity.level().getRecipeManager().getAllRecipesFor(SantaRecipeTypes.ROBO_ELF_TRADING.get());
        this.entity = entity;

        int i = 8 + (RoboElfMenu.TRADES_PER_PAGE-3)*24;
        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 32 + j1 * 18, 139 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 32 + i1 * 18, 197 + i));
        }
    }

    public List<RecipeHolder<RoboElfTradingRecipe>> getRecipes() {
        return recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY; //TODO: Make quick move stack in inventory
    }

    @Override
    public boolean stillValid(Player player) {
        return true; //TODO: Entity info
    }
}
