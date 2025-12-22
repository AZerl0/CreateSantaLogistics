package net.liukrast.santa.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsInputHandler;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ValueSettingsInputHandler.class)
public class ValueSettingsInputHandlerMixin {

    @ModifyExpressionValue(method = "onBlockActivated", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/event/entity/player/PlayerInteractEvent$RightClickBlock;getPos()Lnet/minecraft/core/BlockPos;"))
    private static BlockPos onBlockActivated(BlockPos original, @Local(name = "world") Level level) {
        var state = level.getBlockState(original);
        var b = SantaBlocks.FROSTBURN_ENGINE.get();
        if(!state.is(b)) return original;
        int index = state.getValue(b.getPartsProperty());
        if(index != 12 && index != 22 && index != 4 && index != 14) return original;
        var statePos = b.getPositions().get(index);
        BlockPos ten = b.getPositions().get(10);
        return original
                .offset(statePos.getX(), -statePos.getY(), -statePos.getZ())
                .offset(-ten.getX(), ten.getY(), ten.getZ());
    }

    @ModifyVariable(method = "onBlockActivated", at = @At("STORE"), name = "ray")
    private static BlockHitResult onBlockActivated(BlockHitResult result, @Local(name = "world") Level level) {
        var state = level.getBlockState(result.getBlockPos());
        var b = SantaBlocks.FROSTBURN_ENGINE.get();
        if(!state.is(b)) return result;
        int index = state.getValue(b.getPartsProperty());
        if(index != 12 && index != 22 && index != 4 && index != 14) return result;
        var statePos = b.getPositions().get(index);
        var direction = b.getDirection(state);
        BlockPos origin = b.getOrigin(result.getBlockPos(), statePos, direction);
        BlockPos ten = b.getPositions().get(10);
        return new BlockHitResult(
                result.getLocation()
                        .add(statePos.getX(), -statePos.getY(), -statePos.getZ())
                        .add(-ten.getX(), ten.getY(), ten.getZ()),
                result.getDirection(),
                b.getRelative(origin, statePos, direction),
                result.isInside()
        );
    }
}
