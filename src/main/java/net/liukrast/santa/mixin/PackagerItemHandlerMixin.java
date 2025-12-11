package net.liukrast.santa.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.packager.PackagerItemHandler;
import net.liukrast.santa.world.item.PresentItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PackagerItemHandler.class)
public class PackagerItemHandlerMixin {
    @ModifyReturnValue(method = "isItemValid", at = @At("RETURN"))
    private boolean isItemValid(boolean original, @Local(argsOnly = true, name = "arg2") ItemStack stack) {
        if(stack.getItem() instanceof PresentItem) return false;
        return original;
    }
}
