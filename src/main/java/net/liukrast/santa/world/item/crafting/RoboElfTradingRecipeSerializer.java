package net.liukrast.santa.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.createmod.catnip.codecs.stream.CatnipLargerStreamCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class RoboElfTradingRecipeSerializer implements RecipeSerializer<RoboElfTradingRecipe> {
    public static final MapCodec<RoboElfTradingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            SizedIngredient.NESTED_CODEC.fieldOf("inputA").forGetter(RoboElfTradingRecipe::inputA),
            SizedIngredient.NESTED_CODEC.optionalFieldOf("inputB").forGetter(RoboElfTradingRecipe::inputB),
            SizedIngredient.NESTED_CODEC.optionalFieldOf("inputC").forGetter(RoboElfTradingRecipe::inputC),
            Codec.INT.fieldOf("trustGain").forGetter(RoboElfTradingRecipe::trustGain),
            Codec.INT.fieldOf("energy").forGetter(RoboElfTradingRecipe::energy),
            Codec.INT.fieldOf("processTime").forGetter(RoboElfTradingRecipe::processTime),
            ItemStack.CODEC.fieldOf("result").forGetter(RoboElfTradingRecipe::result)
    ).apply(inst, RoboElfTradingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RoboElfTradingRecipe> STREAM_CODEC = CatnipLargerStreamCodecs.composite(
            SizedIngredient.STREAM_CODEC, RoboElfTradingRecipe::inputA,
            SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs::optional), RoboElfTradingRecipe::inputB,
            SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs::optional), RoboElfTradingRecipe::inputC,
            ByteBufCodecs.INT, RoboElfTradingRecipe::trustGain,
            ByteBufCodecs.INT, RoboElfTradingRecipe::energy,
            ByteBufCodecs.INT, RoboElfTradingRecipe::processTime,
            ItemStack.STREAM_CODEC, RoboElfTradingRecipe::result,
            RoboElfTradingRecipe::new
    );

    @Override
    public MapCodec<RoboElfTradingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RoboElfTradingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
