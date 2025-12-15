package net.liukrast.santa.mixin;

import com.simibubi.create.content.fluids.FlowSource;
import com.simibubi.create.foundation.ICapabilityProvider;
import net.createmod.catnip.math.BlockFace;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * May be replaced with a deployer API feature
 * */
@Deprecated(forRemoval = true)
@Mixin(FlowSource.class)
public interface FlowSourceAccessor {

    @Accessor("location")
    BlockFace getLocation();

    @Accessor("EMPTY")
    static ICapabilityProvider<IFluidHandler> getEMPTY() {
        throw new AssertionError("Mixin injection failed");
    }
}
