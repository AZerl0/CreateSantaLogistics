package net.liukrast.santa.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.box.PackageItem;
import net.liukrast.santa.world.item.PresentItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PackageItem.class)
public class PackageItemMixin {
    @ModifyReturnValue(method = "getContents", at = @At("RETURN"))
    private static ItemStackHandler getContents(ItemStackHandler original, @Local(argsOnly = true, name = "arg0") ItemStack box) {
        if(box.getItem() instanceof PresentItem) return PresentItem.getContents(box);
        return original;
    }
}
