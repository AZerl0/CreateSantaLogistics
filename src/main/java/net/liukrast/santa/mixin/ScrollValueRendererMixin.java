package net.liukrast.santa.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllKeys;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.BulkScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueRenderer;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.outliner.Outliner;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.world.level.block.FrostburnEngineBlock;
import net.liukrast.santa.world.level.block.entity.FrostburnEngineBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ScrollValueRenderer.class)
public abstract class ScrollValueRendererMixin {
    @Shadow
    protected static void addBox(ClientLevel world, BlockPos pos, Direction face, ScrollValueBehaviour behaviour, boolean highlight) {
        throw new AssertionError("Mixin injection failed");
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/BlockHitResult;getDirection()Lnet/minecraft/core/Direction;"), cancellable = true)
    private static void tick(CallbackInfo ci, @Local(name = "pos") BlockPos pos, @Local(name = "world") ClientLevel level, @Local(name = "result") BlockHitResult result, @Local(name = "mc") Minecraft mc, @Local(name = "target") HitResult target) {
        BlockState state = level.getBlockState(pos);
        Direction face = result.getDirection();
        if(!state.is(SantaBlocks.FROSTBURN_ENGINE)) return;

        FrostburnEngineBlock b = SantaBlocks.FROSTBURN_ENGINE.get();

        int index = state.getValue(b.getPartsProperty());
        if(index != 12 && index != 22 && index != 4 && index != 14) return;
        assert mc.player != null;

        var statePos = b.getPositions().get(index);
        var direction = b.getDirection(state);
        BlockPos origin = b.getOrigin(pos, statePos, direction);
        BlockPos ten = b.getPositions().get(10);

        boolean highlightFound = false;
        if(!(level.getBlockEntity(b.getRelative(origin, ten, direction)) instanceof FrostburnEngineBlockEntity febe)) return;

        for (BlockEntityBehaviour blockEntityBehaviour : febe.getAllBehaviours()) {
            if (!(blockEntityBehaviour instanceof ScrollValueBehaviour behaviour))
                continue;

            if (!behaviour.isActive()) {
                Outliner.getInstance().remove(behaviour);
                continue;
            }

            ItemStack mainhandItem = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
            boolean clipboard = behaviour.bypassesInput(mainhandItem);
            if (((ScrollValueBehaviourAccessor)behaviour).getNeedsWrench() && !AllItems.WRENCH.isIn(mainhandItem) && !clipboard)
                continue;
            boolean highlight = behaviour.testHit(target.getLocation()
                    .add(statePos.getX(), -statePos.getY(), -statePos.getZ())
                    .add(-ten.getX(), ten.getY(), ten.getZ())
            ) && !clipboard && !highlightFound;

            if (behaviour instanceof BulkScrollValueBehaviour bulkScrolling && AllKeys.ctrlDown()) {
                for (SmartBlockEntity smartBlockEntity : bulkScrolling.getBulk()) {
                    ScrollValueBehaviour other = smartBlockEntity.getBehaviour(ScrollValueBehaviour.TYPE);
                    if (other != null)
                        addBox(level, smartBlockEntity.getBlockPos(), face, other, highlight);
                }
            } else
                addBox(level, pos, face, behaviour, highlight);

            if (!highlight)
                continue;

            highlightFound = true;
            List<MutableComponent> tip = new ArrayList<>();
            tip.add(behaviour.label.copy());
            tip.add(CreateLang.translateDirect("gui.value_settings.hold_to_edit"));
            CreateClient.VALUE_SETTINGS_HANDLER.showHoverTip(tip);
        }
        ci.cancel();
    }
}
