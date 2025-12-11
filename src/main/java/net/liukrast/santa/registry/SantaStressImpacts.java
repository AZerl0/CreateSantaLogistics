package net.liukrast.santa.registry;

import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

/**
 * Will be replaced with a proper Deployer API
 * */
@Deprecated(forRemoval = true)
public class SantaStressImpacts {
    private SantaStressImpacts() {}

    public static <T extends Block> Consumer<T> withStressCapacity(double value) {
        return block -> BlockStressValues.CAPACITIES.register(block, () -> value);
    }

    public static <T extends Block> Consumer<T> withStressImpact(double value) {
        return block -> BlockStressValues.IMPACTS.register(block, () -> value);
    }
}
