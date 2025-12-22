package net.liukrast.santa.registry;

import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.createmod.catnip.theme.Color;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.mixin.AllFluidsAccessor;
import net.liukrast.santa.world.level.block.FreezingLiquidBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SantaFluids {
    private SantaFluids() {}

    private static final CreateRegistrate REGISTRATE = SantaConstants.registrate();

    public static final FluidEntry<BaseFlowingFluid.Flowing> CRYOLITE = REGISTRATE.standardFluid("cryolite", SolidRenderedPlaceableFluidType.create(0x249fde, () -> 1/8f))
            .properties(b -> b.viscosity(2000).density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2).tickRate(25).slopeFindDistance(3).explosionResistance(300f))
            .source(BaseFlowingFluid.Source::new)
            .block(FreezingLiquidBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(bs -> 4))
            .build()
            .bucket()
            .onRegister(AllFluidsAccessor::invokeRegisterFluidDispenseBehavior)
            .tag(Tags.Items.BUCKETS)
            .build()
            .register();

    public static final FluidEntry<BaseFlowingFluid.Flowing> MOLTEN_SUGAR = REGISTRATE.standardFluid("molten_sugar", SolidRenderedPlaceableFluidType.create(0xa51e2a, () -> 1/8f))
            .properties(b -> b.viscosity(2000).density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2).tickRate(25).slopeFindDistance(3).explosionResistance(300f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(p -> p.mapColor(MapColor.COLOR_RED))
            .build()
            .bucket()
            .onRegister(AllFluidsAccessor::invokeRegisterFluidDispenseBehavior)
            .tag(Tags.Items.BUCKETS)
            .build()
            .register();

    public static void init() {}


    private static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        /*
         * Removing alpha from tint prevents optifine from forcibly applying biome
         * colors to modded fluids (this workaround only works for fluids in the solid
         * render layer)
         */
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
