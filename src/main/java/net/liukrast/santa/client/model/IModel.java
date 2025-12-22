package net.liukrast.santa.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.liukrast.santa.world.level.block.entity.SantaDoorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public interface IModel {
    void setupAnim(SantaDoorBlockEntity blockEntity, float partialTick, float progress);
    void render(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay);
    ResourceLocation getTexture(SantaDoorBlockEntity blockEntity);
    default VertexConsumer getConsumer(SantaDoorBlockEntity be, MultiBufferSource source) {
        return source.getBuffer(RenderType.entityCutoutNoCull(getTexture(be)));
    }

    AABB getRenderBound(AABB original, SantaDoorBlockEntity be, BlockState state, Direction facing);
}
