package net.liukrast.santa.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.liukrast.santa.world.level.block.entity.SantaDoorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;

public interface IModel {
    void setupAnim(SantaDoorBlockEntity blockEntity, float partialTick, float progress);
    void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);
}
