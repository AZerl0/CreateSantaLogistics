package net.liukrast.santa.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.world.level.block.entity.SantaDoorBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SantaDoorModel implements IModel {
    private static final ResourceLocation TEXTURE = SantaConstants.id("textures/entity/santa_door.png");

    private final ModelPart body;
    private final ModelPart lock_1;
    private final ModelPart lock_2;
    private final ModelPart lock_3;
    private final ModelPart lock_4;
    private final ModelPart lock_5;
    private final ModelPart lock_6;
    private final ModelPart large_cog;
    private final ModelPart small_cog;

    public SantaDoorModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        this.body = root.getChild("body");
        this.lock_1 = this.body.getChild("lock_1");
        this.lock_2 = this.body.getChild("lock_2");
        this.lock_3 = this.body.getChild("lock_3");
        this.lock_4 = this.body.getChild("lock_4");
        this.lock_5 = this.body.getChild("lock_5");
        this.lock_6 = this.body.getChild("lock_6");
        this.large_cog = this.body.getChild("large_cog");
        this.small_cog = this.body.getChild("small_cog");
    }

    private static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -64.0F, 0.0F, 64.0F, 64.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 22).addBox(-1.0F, -10.0F, -1.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(140, 22).addBox(-1.0F, -26.0F, -1.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(140, 22).addBox(-1.0F, -42.0F, -1.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(140, 22).addBox(-1.0F, -58.0F, -1.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 24.0F, -3.0F));

        body.addOrReplaceChild("plant", CubeListBuilder.create().texOffs(10, 72).addBox(-16.0F, 0.0F, -6.0F, 30.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 108).addBox(-9.0F, 7.0F, -6.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(97, 72).addBox(-16.0F, 0.0F, -6.0F, 30.0F, 30.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(10, 72).addBox(-16.0F, 0.0F, 6.0F, 30.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 108).addBox(-9.0F, 7.0F, 6.0F, 16.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(97, 72).addBox(-16.0F, 0.0F, 6.0F, 30.0F, 30.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(33.0F, -47.0F, 0.0F));

        body.addOrReplaceChild("lock_1", CubeListBuilder.create().texOffs(140, 11).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 0).addBox(-2.0F, -3.5F, -2.0F, 15.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(51.0F, -47.5F, -2.0F));

        body.addOrReplaceChild("lock_2", CubeListBuilder.create().texOffs(140, 11).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 0).addBox(-2.0F, -3.5F, -2.0F, 15.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(51.0F, -32.0F, -2.0F));

        body.addOrReplaceChild("lock_3", CubeListBuilder.create().texOffs(140, 11).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 0).addBox(-2.0F, -3.5F, -2.0F, 15.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(51.0F, -16.5F, -2.0F));

        body.addOrReplaceChild("lock_4", CubeListBuilder.create().texOffs(140, 11).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 0).addBox(-2.0F, -3.5F, -2.0F, 15.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(51.0F, -47.5F, 8.0F));

        body.addOrReplaceChild("lock_5", CubeListBuilder.create().texOffs(140, 11).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 0).addBox(-2.0F, -3.5F, -2.0F, 15.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(51.0F, -32.0F, 8.0F));

        body.addOrReplaceChild("lock_6", CubeListBuilder.create().texOffs(140, 11).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 0).addBox(-2.0F, -3.5F, -2.0F, 15.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(51.0F, -16.5F, 8.0F));

        body.addOrReplaceChild("large_cog", CubeListBuilder.create().texOffs(178, 22).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(31.0F, -31.0F, 0.0F));

        body.addOrReplaceChild("small_cog", CubeListBuilder.create().texOffs(182, 38).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(34.5F, -34.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(SantaDoorBlockEntity blockEntity, float partialTick, float progress) {
        body.yRot = progress* (Mth.HALF_PI-0.3f);
        lock_1.yRot = (float) (1-Math.pow(2, -100*progress));
        lock_2.yRot = (float) (1-Math.pow(2, -100*progress))*1.5f;
        lock_3.yRot = (float) (1-Math.pow(2, -100*progress))*2;
        lock_4.yRot = -(float) (1-Math.pow(2, -100*progress));
        lock_5.yRot = -(float) (1-Math.pow(2, -100*progress))*1.5f;
        lock_6.yRot = -(float) (1-Math.pow(2, -100*progress))*2;
        assert blockEntity.getLevel() != null;
        float cogSpeed = (blockEntity.getLevel().getGameTime() + partialTick)/2;
        small_cog.zRot = cogSpeed;
        large_cog.zRot = -cogSpeed*0.75f;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        var vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}
