package net.liukrast.santa.datagen.loot;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SantaBlockLootSubProvider extends BlockLootSubProvider {
    public SantaBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        List<Predicate<Block>> exceptions = List.of(
                not(SantaBlocks.CRYOLITE_CLUSTER),
                not(SantaBlocks.SMALL_CRYOLITE_BUD),
                not(SantaBlocks.MEDIUM_CRYOLITE_BUD),
                not(SantaBlocks.LARGE_CRYOLITE_BUD)
        );
        SantaConstants.getElements(BuiltInRegistries.BLOCK).filter(b -> b.getLootTable() != BuiltInLootTables.EMPTY && exceptions.stream().allMatch(k -> k.test(b))).forEach(this::dropSelf);
        add(
                SantaBlocks.CRYOLITE_CLUSTER.get(),
                b -> createSilkTouchDispatchTable(
                        b,
                        LootItem.lootTableItem(SantaItems.CRYOLITE_SHARD)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))
                                .otherwise(
                                        this.applyExplosionDecay(b, LootItem.lootTableItem(SantaItems.CRYOLITE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
                                )
                )
        );
        this.dropWhenSilkTouch(SantaBlocks.SMALL_CRYOLITE_BUD.get());
        this.dropWhenSilkTouch(SantaBlocks.MEDIUM_CRYOLITE_BUD.get());
        this.dropWhenSilkTouch(SantaBlocks.LARGE_CRYOLITE_BUD.get());

    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(b -> BuiltInRegistries.BLOCK.getKey(b).getNamespace().equals(SantaConstants.MOD_ID))
                .toList();
    }

    private Predicate<Block> not(Supplier<? extends Block> block) {
        return b -> b != block.get();
    }
}
