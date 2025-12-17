package net.liukrast.santa.mixin;

import com.simibubi.create.AllFluids;
import net.minecraft.world.item.BucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AllFluids.class)
public interface AllFluidsAccessor {
    @Invoker("registerFluidDispenseBehavior")
    static void invokeRegisterFluidDispenseBehavior(BucketItem bucket) {
        throw new AssertionError("Mixin Injection Failed");
    }
}
