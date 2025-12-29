package net.liukrast.santa.world.entity;

import net.createmod.catnip.codecs.stream.CatnipLargerStreamCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;

import java.util.Optional;

@Deprecated(forRemoval = true)
public class TradeInfo {
    private final ItemCost[] ingredients;
    private final ItemStack result;
    private final int trustGain;
    private final int energy;
    private final int processTime;

    public TradeInfo(ItemCost a, ItemStack result, int trustGain, int energy, int processTime) {
        this(a, null, result, trustGain, energy, processTime);
    }

    public TradeInfo(ItemCost a, ItemCost b, ItemStack result, int trustGain, int energy, int processTime) {
        this(a, b, null, result, trustGain, energy, processTime);
    }

    public TradeInfo(ItemCost a, ItemCost b, ItemCost c, ItemStack result, int trustGain, int energy, int processTime) {
        ingredients = new ItemCost[]{a,b,c};
        this.result = result;
        this.trustGain = trustGain;
        this.energy = energy;
        this.processTime = processTime;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, TradeInfo> STREAM_CODEC = CatnipLargerStreamCodecs.composite(
            ItemCost.STREAM_CODEC, i -> i.ingredients[0],
            ItemCost.OPTIONAL_STREAM_CODEC, i -> Optional.ofNullable(i.ingredients[1]),
            ItemCost.OPTIONAL_STREAM_CODEC, i -> Optional.ofNullable(i.ingredients[2]),
            ItemStack.STREAM_CODEC, TradeInfo::getResult,
            ByteBufCodecs.INT, TradeInfo::getTrustGain,
            ByteBufCodecs.INT, TradeInfo::getEnergy,
            ByteBufCodecs.INT, TradeInfo::getProcessTime,
            (a,b,c,e,f,g,h) -> new TradeInfo(a,b.orElse(null),c.orElse(null),e,f,g,h)
    );

    public ItemCost[] getIngredients() {
        return ingredients;
    }

    public int getTrustGain() {
        return trustGain;
    }

    public int getEnergy() {
        return energy;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getProcessTime() {
        return processTime;
    }
}
