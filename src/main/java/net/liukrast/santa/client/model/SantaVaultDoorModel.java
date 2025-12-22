package net.liukrast.santa.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.world.level.block.entity.SantaDoorBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SantaVaultDoorModel implements IModel {
    private static final ResourceLocation TEXTURE = SantaConstants.id("textures/entity/santa_vault_door.png");

    private final ModelPart left;
    private final ModelPart right;

    public SantaVaultDoorModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.left = root.getChild("left");
        this.right = root.getChild("right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("left", CubeListBuilder.create().texOffs(78, 0).addBox(-32.0F, -64.0F, -1.0F, 32.0F, 64.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 100).addBox(-24.0F, -48.0F, 0.0F, 16.0F, 32.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 66).addBox(-24.0F, -48.0F, -1.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(78, 66).addBox(-40.0F, -64.0F, -1.0F, 8.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(78, 102).addBox(-40.0F, -16.0F, -1.0F, 8.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(78, 84).addBox(-48.0F, -48.0F, -1.0F, 16.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(72.0F, 24.0F, 0.0F));

        partdefinition.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -64.0F, -1.0F, 32.0F, 64.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 66).addBox(8.0F, -48.0F, -1.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 100).addBox(8.0F, -48.0F, 0.0F, 16.0F, 32.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(34, 66).addBox(32.0F, -64.0F, -1.0F, 8.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(34, 102).addBox(32.0F, -16.0F, -1.0F, 8.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(34, 84).addBox(32.0F, -32.0F, -1.0F, 16.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(SantaDoorBlockEntity blockEntity, float partialTick, float progress) {
        right.x = -8-progress*40.01f;
        left.x = 64+8+progress*40.01f;
    }

    @Override
    public void render(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay) {
        right.render(poseStack, consumer, packedLight, packedOverlay);
        left.render(poseStack, consumer, packedLight, packedOverlay);
    }

    @Override
    public ResourceLocation getTexture(SantaDoorBlockEntity blockEntity) {
        return TEXTURE;
    }

    @Override
    public AABB getRenderBound(AABB original, SantaDoorBlockEntity be, BlockState state, Direction dir) {
        dir = dir.getCounterClockWise();
        return original
                .expandTowards(new Vec3(dir.getStepX()*-2.5, 0, dir.getStepZ()*-2.5))
                .expandTowards(new Vec3(dir.getStepX()*6.5, 3, dir.getStepZ()*6.5));
    }
}
