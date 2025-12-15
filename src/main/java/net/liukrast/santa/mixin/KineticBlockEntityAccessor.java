package net.liukrast.santa.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticEffectHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Will be replaced with deployer API
 * */
@Deprecated(forRemoval = true)
@Mixin(KineticBlockEntity.class)
public interface KineticBlockEntityAccessor {
    @Accessor("effects")
    KineticEffectHandler getEffects();
}
