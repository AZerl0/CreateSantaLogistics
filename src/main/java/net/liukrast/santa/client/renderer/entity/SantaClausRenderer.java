package net.liukrast.santa.client.renderer.entity;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.client.model.SantaClausModel;
import net.liukrast.santa.world.entity.SantaClaus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class SantaClausRenderer extends MobRenderer<SantaClaus, SantaClausModel> {
    private static final ResourceLocation TEXTURE = SantaConstants.id("textures/entity/santa_claus.png");

    public SantaClausRenderer(EntityRendererProvider.Context context) {
        super(context, new SantaClausModel(context.bakeLayer(SantaClausModel.LAYER_LOCATION)), 2);
        addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(SantaClaus entity) {
        return TEXTURE;
    }
}
