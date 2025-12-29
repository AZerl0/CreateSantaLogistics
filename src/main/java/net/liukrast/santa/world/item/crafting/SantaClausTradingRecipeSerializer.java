package net.liukrast.santa.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class SantaClausTradingRecipeSerializer implements RecipeSerializer<SantaClausTradingRecipe> {
    public static final MapCodec<SantaClausTradingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            SizedIngredient.NESTED_CODEC.fieldOf("input").forGetter(SantaClausTradingRecipe::input),
            Codec.INT.fieldOf("trustRequired").forGetter(SantaClausTradingRecipe::trustRequired),
            Codec.BOOL.fieldOf("food").forGetter(SantaClausTradingRecipe::food),
            ItemStack.CODEC.fieldOf("result").forGetter(SantaClausTradingRecipe::result)
    ).apply(inst, SantaClausTradingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SantaClausTradingRecipe> STREAM_CODEC = StreamCodec.composite(
            SizedIngredient.STREAM_CODEC, SantaClausTradingRecipe::input,
            ByteBufCodecs.INT, SantaClausTradingRecipe::trustRequired,
            ByteBufCodecs.BOOL, SantaClausTradingRecipe::food,
            ItemStack.STREAM_CODEC, SantaClausTradingRecipe::result,
            SantaClausTradingRecipe::new
    );

    @Override
    public MapCodec<SantaClausTradingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SantaClausTradingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
