package net.liukrast.santa.mixin;

import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ScrollValueBehaviour.class)
public interface ScrollValueBehaviourAccessor {
    @Accessor("needsWrench")
    boolean getNeedsWrench();
}
