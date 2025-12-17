package net.liukrast.santa.world.inventory;

import net.liukrast.santa.registry.SantaMenuTypes;
import net.liukrast.santa.world.entity.TradeInfo;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class RoboElfMenu extends AbstractContainerMenu {
    public final List<TradeInfo> trades;

    public RoboElfMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buf) {
        this(containerId, playerInventory, buf.readList(TradeInfo.STREAM_CODEC));
    }


    public RoboElfMenu(int containerId, Inventory playerInventory, List<TradeInfo> trades) {
        super(SantaMenuTypes.ROBO_ELF_MENU.get(), containerId);
        this.trades = trades;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
