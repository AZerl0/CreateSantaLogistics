package net.liukrast.santa.registry;

import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.world.item.FrostburnCoreItem;
import net.liukrast.santa.world.item.PresentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class SantaItems {
    private SantaItems() {}
    static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(SantaConstants.MOD_ID);

    public static final DeferredItem<Item> CRYOLITE_SHARD = register("cryolite_shard", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CRYOLITE_POWDER = register("cryolite_powder", () -> new Item(new Item.Properties()));

    public static final DeferredItem<DeferredSpawnEggItem> ROBO_ELF_SPAWN_EGG = register("robo_elf_spawn_egg", () -> new DeferredSpawnEggItem(SantaEntityTypes.ROBO_ELF, 0xe4b763, 0x61a53f, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> SANTA_CLAUS_SPAWN_EGG = register("santa_claus_spawn_egg", () -> new DeferredSpawnEggItem(SantaEntityTypes.SANTA_CLAUS, 0xFF0000, 0xFFFFFF, new Item.Properties()));
    public static final DeferredItem<Item> CANDY_CANE = register("candy_cane", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FROSTBURN_CORE = register("frostburn_core", () -> new FrostburnCoreItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final DeferredItem<Item> SANTA_KEY = register("santa_key", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final List<DeferredItem<PresentItem>> PRESENTS = SantaPackages.PRESENTS.stream()
            .map(k -> register(k.type() + "_present", () -> new PresentItem(new Item.Properties().stacksTo(1), k))).toList();

    public static void init(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        return REGISTER.register(name, () -> {
            T value = item.get();
            var modifier = new ItemDescription.Modifier(value, FontHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(value)));
            TooltipModifier.REGISTRY.register(value, modifier);
            return value;
        });
    }
}
