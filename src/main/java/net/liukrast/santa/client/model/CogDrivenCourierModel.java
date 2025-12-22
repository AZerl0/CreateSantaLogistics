package net.liukrast.santa.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class CogDrivenCourierModel extends Model {
    private final ModelPart body;
    private final ModelPart couch;
    private final ModelPart couch_back;
    private final ModelPart bag;
    private final ModelPart end;
    private final ModelPart back;
    private final ModelPart right_slide;
    private final ModelPart left_slide;
    private final ModelPart right_side;
    private final ModelPart left_side;

    public CogDrivenCourierModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.body = root.getChild("body");
        this.couch = this.body.getChild("couch");
        this.couch_back = this.couch.getChild("couch_back");
        this.bag = this.body.getChild("bag");
        this.end = this.bag.getChild("end");
        this.back = this.body.getChild("back");
        this.right_slide = this.body.getChild("right_slide");
        this.left_slide = this.body.getChild("left_slide");
        this.right_side = this.body.getChild("right_side");
        this.left_side = this.body.getChild("left_side");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 174).addBox(-16.0F, -15.0F, -11.0F, 32.0F, 15.0F, 55.0F, new CubeDeformation(0.0F))
                .texOffs(256, 47).addBox(-29.0F, -20.0F, 25.0F, 13.0F, 13.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(302, 272).addBox(-29.0F, -10.0F, 12.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(302, 272).addBox(-29.0F, -17.0F, 12.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(256, 47).mirror().addBox(16.0F, -20.0F, 25.0F, 13.0F, 13.0F, 29.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(302, 272).mirror().addBox(16.0F, -17.0F, 12.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(302, 272).mirror().addBox(16.0F, -10.0F, 12.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(-22.0F, -36.0F, -61.0F, 0.0F, 46.0F, 128.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(22.0F, -36.0F, -61.0F, 0.0F, 46.0F, 128.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 14.0F, 0.0F));

        PartDefinition couch = body.addOrReplaceChild("couch", CubeListBuilder.create().texOffs(0, 244).addBox(-4.0F, 7.0F, -63.0F, 30.0F, 8.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -27.0F, 42.0F));

        PartDefinition couch_back = couch.addOrReplaceChild("couch_back", CubeListBuilder.create().texOffs(80, 286).addBox(-15.0F, -32.0F, -4.0F, 30.0F, 31.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(11.0F, 13.0F, -31.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition bag = body.addOrReplaceChild("bag", CubeListBuilder.create().texOffs(174, 241).addBox(-16.0F, -33.0F, -12.0F, 32.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 27.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition end = bag.addOrReplaceChild("end", CubeListBuilder.create().texOffs(256, 144).addBox(-8.0F, -11.0F, -8.0F, 16.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(-53, 75).addBox(-5.0F, -1.0F, -2.0F, 16.0F, 0.0F, 53.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -33.0F, 3.0F, -0.0482F, -0.2595F, 0.035F));

        PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 286).addBox(-17.0F, -31.0F, -3.0F, 34.0F, 32.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 45.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition right_slide = body.addOrReplaceChild("right_slide", CubeListBuilder.create().texOffs(256, 0).addBox(-13.0F, 0.0F, -10.0F, 14.0F, 0.0F, 47.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -2.0F, 0.0F, 0.0F, 0.0F, -1.0908F));

        PartDefinition left_slide = body.addOrReplaceChild("left_slide", CubeListBuilder.create().texOffs(256, 0).mirror().addBox(-1.0F, 0.0F, -10.0F, 14.0F, 0.0F, 47.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(16.0F, -2.0F, 0.0F, 0.0F, 0.0F, 1.0908F));

        PartDefinition right_side = body.addOrReplaceChild("right_side", CubeListBuilder.create().texOffs(174, 174).addBox(-3.0F, -4.0F, -59.0F, 8.0F, 8.0F, 59.0F, new CubeDeformation(0.0F))
                .texOffs(256, 89).addBox(3.0F, -2.0F, -32.0F, 2.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0F, -31.0F, 45.0F, 0.0F, 0.0436F, 0.0F));

        PartDefinition left_side = body.addOrReplaceChild("left_side", CubeListBuilder.create().texOffs(174, 174).mirror().addBox(-5.0F, -4.0F, -59.0F, 8.0F, 8.0F, 59.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(256, 89).mirror().addBox(-5.0F, -2.0F, -32.0F, 2.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(20.0F, -31.0F, 45.0F, 0.0F, -0.0436F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    public void setupAnim(float pitch, float yaw, float ageInTicks, boolean walk) {
        body.yRot=yaw;
        body.xRot = pitch;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
