package net.liukrast.santa.registry;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.liukrast.santa.SantaConstants;

public class SantaFluids {
    private SantaFluids() {}

    private static final CreateRegistrate REGISTRATE = SantaConstants.registrate();

    public static final FluidEntry<VirtualFluid> CRYOLITE = REGISTRATE.virtualFluid("cryolite")
            .register();

    public static void init() {}
}
