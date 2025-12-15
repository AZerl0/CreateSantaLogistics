package net.liukrast.santa.world.level.block.entity;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import joptsimple.internal.Strings;
import net.liukrast.santa.SantaLang;
import net.liukrast.santa.registry.SantaBlockEntityTypes;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class FrostburnEngineBlockEntity extends GeneratingKineticBlockEntity {
    private int temperature = 0;
    public ScrollValueBehaviour overclock;

    private static final int MAX_TEMPERATURE = 10000;

    private final IFluidHandler handler = new IFluidHandler() {
        @Override
        public int getTanks() {
            return 0;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return FluidStack.EMPTY;
        }

        @Override
        public int getTankCapacity(int tank) {
            return 0;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!resource.is(SantaFluids.CRYOLITE.getType())) return 0;
            if (temperature <= 0) return 0;
            int accepted = Math.min(temperature, resource.getAmount());
            if (action.execute()) temperature -= accepted;
            return accepted;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return FluidStack.EMPTY;
        }
    };

    public FrostburnEngineBlockEntity(BlockPos pos, BlockState state) {
        super(SantaBlockEntityTypes.FROSTBURN_ENGINE.get(), pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        overclock = new ScrollValueBehaviour(Component.empty(), this, new CenteredSideValueBoxTransform((b,s) -> false));
        overclock.between(1, 1024);
        behaviours.add(overclock);
    }

    public IFluidHandler getHandler() {
        return handler;
    }

    @Override
    public float getGeneratedSpeed() {
        if(!getBlockState().is(SantaBlocks.FROSTBURN_ENGINE.get()))
            return 0;
        return 16;
    }

    @Override
    public float calculateAddedStressCapacity() {
        return super.calculateAddedStressCapacity() * overclock.value * 16;
    }

    @Override
    public void initialize() {
        super.initialize();
        if(!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed())
            updateGeneratedRotation();
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putInt("Temperature", temperature);
        compound.putInt("Overclock", overclock.value);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if(clientPacket) temperature = (temperature+ compound.getInt("Temperature"))/2;
        else temperature = compound.getInt("Temperature");
        overclock.setValue(compound.getInt("Overclock"));
    }

    @Override
    public void tick() {
        super.tick();
        temperature = Mth.clamp(temperature + overclock.value, 0, MAX_TEMPERATURE);
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        SantaLang.translate("tooltip.overclock").style(ChatFormatting.GRAY).forGoggles(tooltip);
        var color = overclock.value < 256 ? ChatFormatting.GREEN : overclock.value < 512 ? ChatFormatting.YELLOW : overclock.value < 768 ? ChatFormatting.GOLD : ChatFormatting.RED;
        SantaLang.number(overclock.value).add(Component.literal("°")).style(color).add(Component.literal(" ")).add(SantaLang.translateDirect("tooltip.temperature_per_tick").withStyle(ChatFormatting.DARK_GRAY)).forGoggles(tooltip, 1);
        SantaLang.translate("tooltip.temperature").style(ChatFormatting.GRAY).forGoggles(tooltip);
        int bars = 40;

        var c = Component.literal("     ")
                .append(bars(temperature*bars/MAX_TEMPERATURE, ChatFormatting.RED))
                .append(bars(bars - (temperature*bars/MAX_TEMPERATURE), ChatFormatting.GREEN));

        tooltip.add(isPlayerSneaking ? c.withStyle(ChatFormatting.DARK_GRAY).append(Component.literal(" (")).append(String.valueOf(temperature)).append(")") : c);
        return true;
    }

    private MutableComponent bars(int level, ChatFormatting format) {
        return Component.literal(Strings.repeat('|', level))
                .withStyle(format);
    }
}
