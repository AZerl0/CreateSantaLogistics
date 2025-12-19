package net.liukrast.santa.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.liukrast.santa.registry.SantaPartialModels;
import net.liukrast.santa.world.level.block.entity.FrostburnEngineBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class FrostburnEngineRenderer extends KineticBlockEntityRenderer<FrostburnEngineBlockEntity> {
    public FrostburnEngineRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(FrostburnEngineBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

        CachedBuffers.partial(SantaPartialModels.FROSTBURN_CORE, be.getBlockState())
                .renderInto(ms, vb);


        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        Direction direction = Direction.UP;


        assert be.getLevel() != null;
        int lightBehind = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().relative(direction.getOpposite()));

        SuperByteBuffer shaftHalf =
                CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, be.getBlockState(), direction.getOpposite());

        standardKineticRotationTransform(shaftHalf, be, lightBehind).renderInto(ms, vb);
    }
}
