package net.liukrast.santa.registry;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.world.item.PresentItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class SantaItems {
    private SantaItems() {}
    private static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(SantaConstants.MOD_ID);

    public static final DeferredItem<DeferredSpawnEggItem> ROBO_ELF_SPAWN_EGG = REGISTER.register("robo_elf_spawn_egg", () -> new DeferredSpawnEggItem(SantaEntityTypes.ROBO_ELF, 0xe4b763, 0x61a53f, new Item.Properties()));
    public static final List<DeferredItem<PresentItem>> PRESENTS = SantaPackages.PRESENTS.stream()
            .map(k -> REGISTER.register(k.type() + "_present", () -> new PresentItem(new Item.Properties().stacksTo(1), k))).toList();

    public static void init(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
