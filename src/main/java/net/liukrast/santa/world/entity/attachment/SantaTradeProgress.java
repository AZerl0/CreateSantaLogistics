package net.liukrast.santa.world.entity.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipe;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipeSerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public record SantaTradeProgress(int progress, SantaClausTradingRecipe recipe) {

    public static final Codec<SantaTradeProgress> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("progress").forGetter(SantaTradeProgress::progress),
            SantaClausTradingRecipeSerializer.CODEC.fieldOf("recipe").forGetter(SantaTradeProgress::recipe)
    ).apply(inst, SantaTradeProgress::new));

    public static final Codec<Optional<SantaTradeProgress>> OPTIONAL_CODEC = ExtraCodecs.optionalEmptyMap(SantaTradeProgress.CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, SantaTradeProgress> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SantaTradeProgress::progress,
            SantaClausTradingRecipeSerializer.STREAM_CODEC, SantaTradeProgress::recipe,
            SantaTradeProgress::new
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<SantaTradeProgress>> OPTIONAL_STREAM_CODEC = STREAM_CODEC.apply(ByteBufCodecs::optional);

    public SantaTradeProgress increment() {
        return new SantaTradeProgress(progress+1, recipe);
    }
}
