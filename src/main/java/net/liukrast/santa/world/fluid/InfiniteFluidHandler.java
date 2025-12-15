package net.liukrast.santa.world.fluid;

import net.liukrast.santa.registry.SantaFluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class InfiniteFluidHandler implements IFluidHandler {
    public InfiniteFluidHandler() {}

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return new FluidStack(SantaFluids.CRYOLITE.get(), 1000);
    }

    @Override
    public int getTankCapacity(int tank) {
        return 1000;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return resource.getAmount();
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !FluidStack.isSameFluidSameComponents(resource, resource)) {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), FluidAction.SIMULATE);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if(maxDrain <= 0) return FluidStack.EMPTY;
        return new FluidStack(SantaFluids.CRYOLITE.get(), maxDrain);
    }
}
