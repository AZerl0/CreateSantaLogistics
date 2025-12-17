package net.liukrast.santa;

import com.simibubi.create.api.registrate.CreateRegistrateRegistrationCallback;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateProvider;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

public class SantaRegistrate extends CreateRegistrate {
    protected SantaRegistrate(String modid) {
        super(modid);
    }

    public static SantaRegistrate create(String modid) {
        SantaRegistrate registrate = new SantaRegistrate(modid);
        // The registrate is registered here instead of in the constructor so that if a subclass
        // overrides the addRegisterCallback to be dependent on some sort of state initialized in the constructor,
        // it won't explode. The consequence is that subclasses must manually provide their registrate to the callback API
        CreateRegistrateRegistrationCallback.provideRegistrate(registrate);
        return registrate;
    }

    @Override
    public <T extends RegistrateProvider> CreateRegistrate addDataGenerator(ProviderType<? extends T> type, NonNullConsumer<? extends T> cons) {
        if(type == ProviderType.LANG) return this; //I don't want registrate's ass to generate language for me.
        return super.addDataGenerator(type, cons);
    }
}
