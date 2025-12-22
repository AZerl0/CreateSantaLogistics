package net.liukrast.santa.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsClient;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ValueSettingsClient.class)
public class ValueSettingsClientMixin {
    @Shadow
    private Minecraft mc;

    @ModifyVariable(method = "tick", at = @At("STORE"), name = "hitResult")
    private HitResult tick(HitResult result) {
        if(!(result instanceof BlockHitResult blockHit)) return result;
        assert mc.level != null;
        var state = mc.level.getBlockState(blockHit.getBlockPos());
        var b = SantaBlocks.FROSTBURN_ENGINE.get();
        if(!state.is(b)) return result;
        int index = state.getValue(b.getPartsProperty());
        if(index != 12 && index != 22 && index != 4 && index != 14) return result;
        var statePos = b.getPositions().get(index);
        var direction = b.getDirection(state);
        BlockPos origin = b.getOrigin(blockHit.getBlockPos(), statePos, direction);
        BlockPos ten = b.getPositions().get(10);
        return new BlockHitResult(
                blockHit.getLocation()
                        .add(statePos.getX(), -statePos.getY(), -statePos.getZ())
                        .add(-ten.getX(), ten.getY(), ten.getZ()),
                blockHit.getDirection(),
                b.getRelative(origin, ten, direction),
                blockHit.isInside()
        );
    }

    @ModifyExpressionValue(method = "cancelIfWarmupAlreadyStarted", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/event/entity/player/PlayerInteractEvent$RightClickBlock;getPos()Lnet/minecraft/core/BlockPos;"))
    private BlockPos cancelIfWarmupAlreadyStarted(BlockPos original) {
        assert mc.level != null;
        var state = mc.level.getBlockState(original);
        var b = SantaBlocks.FROSTBURN_ENGINE.get();
        if(!state.is(b)) return original;
        int index = state.getValue(b.getPartsProperty());
        if(index != 12 && index != 22 && index != 4 && index != 14) return original;
        var statePos = b.getPositions().get(index);
        var direction = b.getDirection(state);
        BlockPos origin = b.getOrigin(original, statePos, direction);
        BlockPos ten = b.getPositions().get(10);
        return b.getRelative(origin, ten, direction);
    }
}
