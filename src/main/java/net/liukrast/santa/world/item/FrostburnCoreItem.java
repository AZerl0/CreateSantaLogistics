package net.liukrast.santa.world.item;

import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.liukrast.santa.client.renderer.item.FrostburnCoreRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FrostburnCoreItem extends Item {
    public FrostburnCoreItem(Properties properties) {
        super(properties);
    }


    @SuppressWarnings("removal")
    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new FrostburnCoreRenderer()));
    }
}
