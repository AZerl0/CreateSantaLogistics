package net.liukrast.santa.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageRenderer;
import net.liukrast.santa.world.item.PresentItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * A Deployer API will replace this
 * */
@Deprecated
@Mixin(PackageRenderer.class)
public class PackageRendererMixin {
    @WrapOperation(method = "renderBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;solid()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType renderBox(Operation<RenderType> original, @Local(argsOnly = true, name = "arg0") Entity entity) {
        if(!(entity instanceof PackageEntity pe)) return original.call();
        if(pe.box.getItem() instanceof PresentItem) return RenderType.cutout();
        return original.call();
    }
}
