package net.liukrast.santa.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.FluidPropagator;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Deprecated(forRemoval = true)
@Mixin(FluidPropagator.class)
public class FluidPropagatorMixin {
    @ModifyReturnValue(method = "hasFluidCapability", at = @At("RETURN"))
    private static boolean hasFluidCapability(boolean original, @Local(argsOnly = true, name = "arg0") BlockGetter world, @Local BlockPos pos, @Local Direction side) {
        BlockState state = world.getBlockState(pos);
        if(!SantaConstants.fluidCapabilityExtension(state.getBlock())) return original;
        if(!(world instanceof Level level)) return original;
        return level.getCapability(Capabilities.FluidHandler.BLOCK, pos, side) != null;
    }
}
