package net.liukrast.santa.registry;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.world.item.crafting.RoboElfTradingRecipe;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SantaRecipeTypes {
    private SantaRecipeTypes() {}

    private static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, SantaConstants.MOD_ID);

    public static final Supplier<RecipeType<RoboElfTradingRecipe>> ROBO_ELF_TRADING = REGISTER.register("robo_elf_trading", () -> RecipeType.simple(SantaConstants.id("robo_elf_trading")));
    public static final Supplier<RecipeType<SantaClausTradingRecipe>> SANTA_CLAUS_TRADING = REGISTER.register("santa_claus_trading", () -> RecipeType.simple(SantaConstants.id("santa_claus_trading")));

    public static void init(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
