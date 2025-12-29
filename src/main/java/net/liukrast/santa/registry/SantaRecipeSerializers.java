package net.liukrast.santa.registry;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipe;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipeSerializer;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipe;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SantaRecipeSerializers {
    private SantaRecipeSerializers() {}

    private static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, SantaConstants.MOD_ID);

    public static final Supplier<RecipeSerializer<RoboElfTradingRecipe>> ROBO_ELF_TRADING = REGISTER.register("robo_elf_trading", RoboElfTradingRecipeSerializer::new);
    public static final Supplier<RecipeSerializer<SantaClausTradingRecipe>> SANTA_CLAUS_TRADING = REGISTER.register("santa_claus_trading", SantaClausTradingRecipeSerializer::new);

    public static void init(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
