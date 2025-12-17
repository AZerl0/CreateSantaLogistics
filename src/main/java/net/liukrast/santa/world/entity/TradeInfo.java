package net.liukrast.santa.world.entity;

import net.createmod.catnip.codecs.stream.CatnipLargerStreamCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record TradeInfo(ItemStack a, ItemStack b, ItemStack c, ItemStack d, ItemStack result, int trustGain, int energy) {
    //TODO: Data-driven
    public static final StreamCodec<RegistryFriendlyByteBuf, TradeInfo> STREAM_CODEC = CatnipLargerStreamCodecs.composite(
            ItemStack.STREAM_CODEC, TradeInfo::a,
            ItemStack.STREAM_CODEC, TradeInfo::b,
            ItemStack.STREAM_CODEC, TradeInfo::c,
            ItemStack.STREAM_CODEC, TradeInfo::d,
            ItemStack.STREAM_CODEC, TradeInfo::result,
            ByteBufCodecs.INT, TradeInfo::trustGain,
            ByteBufCodecs.INT, TradeInfo::energy,
            TradeInfo::new
    );

}
