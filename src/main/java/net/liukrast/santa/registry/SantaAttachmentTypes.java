package net.liukrast.santa.registry;

import com.mojang.serialization.Codec;
import net.liukrast.santa.SantaConstants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class SantaAttachmentTypes {
    private SantaAttachmentTypes() {}

    private static final DeferredRegister<AttachmentType<?>> REGISTER = SantaConstants.createDeferred(NeoForgeRegistries.ATTACHMENT_TYPES);

    public static final Supplier<AttachmentType<Integer>> TRUST = REGISTER.register(
            "trust", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static void init(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
