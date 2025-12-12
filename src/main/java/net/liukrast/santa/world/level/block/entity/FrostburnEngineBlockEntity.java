package net.liukrast.santa.world.level.block.entity;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import net.liukrast.santa.registry.SantaBlockEntityTypes;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FrostburnEngineBlockEntity extends GeneratingKineticBlockEntity {

    private final IFluidHandler handler = new IFluidHandler() {
        @Override
        public int getTanks() {
            return 0;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return null;
        }

        @Override
        public int getTankCapacity(int tank) {
            return 0;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return null;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return null;
        }
    };


    public FrostburnEngineBlockEntity(BlockPos pos, BlockState state) {
        super(SantaBlockEntityTypes.FROSTBURN_ENGINE.get(), pos, state);
    }

    public IFluidHandler getHandler() {
        return handler;
    }

    @Override
    public float getGeneratedSpeed() {
        if(!getBlockState().is(SantaBlocks.FROSTBURN_ENGINE.get()))
            return 0;
        return 16;
    }

    @Override
    public float calculateAddedStressCapacity() {
        return super.calculateAddedStressCapacity() * 16777216;
    }

    @Override
    public void initialize() {
        super.initialize();
        if(!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed())
            updateGeneratedRotation();
    }
}
