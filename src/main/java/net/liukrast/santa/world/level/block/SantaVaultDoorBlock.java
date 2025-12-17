package net.liukrast.santa.world.level.block;

import net.liukrast.santa.registry.SantaItems;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class SantaVaultDoorBlock extends SantaDoorBlock {
    public SantaVaultDoorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getWidth() {
        return 5;
    }

    @Override
    public boolean canUnlock(ItemStack stack) {
        return stack.is(SantaItems.SANTA_KEY);
    }
}
