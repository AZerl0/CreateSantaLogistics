package net.liukrast.santa.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class RudolfModel extends Model {
    private final ModelPart bone;
    private final ModelPart body2;
    private final ModelPart tail2;
    private final ModelPart back_left2;
    private final ModelPart back_left_leg2;
    private final ModelPart back_left_foot2;
    private final ModelPart back_right2;
    private final ModelPart back_right_leg2;
    private final ModelPart back_right_foot3;
    private final ModelPart neck2;
    private final ModelPart head2;
    private final ModelPart front_left2;
    private final ModelPart front_left_leg2;
    private final ModelPart front_left_foot2;
    private final ModelPart front_right2;
    private final ModelPart front_right_leg2;
    private final ModelPart front_right_foot2;
    private final ModelPart engine2;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart back_left;
    private final ModelPart back_left_leg;
    private final ModelPart back_left_foot;
    private final ModelPart back_right;
    private final ModelPart back_right_leg;
    private final ModelPart back_right_foot;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart front_left;
    private final ModelPart front_left_leg;
    private final ModelPart front_left_foot;
    private final ModelPart front_right;
    private final ModelPart front_right_leg;
    private final ModelPart front_right_foot;
    private final ModelPart engine;

    public RudolfModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.bone = root.getChild("bone");
        this.body2 = this.bone.getChild("body2");
        this.tail2 = this.body2.getChild("tail2");
        this.back_left2 = this.body2.getChild("back_left2");
        this.back_left_leg2 = this.back_left2.getChild("back_left_leg2");
        this.back_left_foot2 = this.back_left_leg2.getChild("back_left_foot2");
        this.back_right2 = this.body2.getChild("back_right2");
        this.back_right_leg2 = this.back_right2.getChild("back_right_leg2");
        this.back_right_foot3 = this.back_right_leg2.getChild("back_right_foot3");
        this.neck2 = this.body2.getChild("neck2");
        this.head2 = this.neck2.getChild("head2");
        this.front_left2 = this.body2.getChild("front_left2");
        this.front_left_leg2 = this.front_left2.getChild("front_left_leg2");
        this.front_left_foot2 = this.front_left_leg2.getChild("front_left_foot2");
        this.front_right2 = this.body2.getChild("front_right2");
        this.front_right_leg2 = this.front_right2.getChild("front_right_leg2");
        this.front_right_foot2 = this.front_right_leg2.getChild("front_right_foot2");
        this.engine2 = this.body2.getChild("engine2");
        this.body = this.bone.getChild("body");
        this.tail = this.body.getChild("tail");
        this.back_left = this.body.getChild("back_left");
        this.back_left_leg = this.back_left.getChild("back_left_leg");
        this.back_left_foot = this.back_left_leg.getChild("back_left_foot");
        this.back_right = this.body.getChild("back_right");
        this.back_right_leg = this.back_right.getChild("back_right_leg");
        this.back_right_foot = this.back_right_leg.getChild("back_right_foot");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.front_left = this.body.getChild("front_left");
        this.front_left_leg = this.front_left.getChild("front_left_leg");
        this.front_left_foot = this.front_left_leg.getChild("front_left_foot");
        this.front_right = this.body.getChild("front_right");
        this.front_right_leg = this.front_right.getChild("front_right_leg");
        this.front_right_foot = this.front_right_leg.getChild("front_right_foot");
        this.engine = this.body.getChild("engine");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body2 = bone.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(32, 7).addBox(-4.5F, -8.0F, -17.0F, 9.0F, 9.0F, 22.0F, new CubeDeformation(0.05F))
                .texOffs(72, 0).addBox(-3.0F, -10.0F, -19.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-4.0F, -7.0F, 1.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(24.0F, -17.0F, 6.0F));

        PartDefinition tail2 = body2.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(43, 48).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 66).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 4.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition back_left2 = body2.addOrReplaceChild("back_left2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -2.0F, 3.0F));

        PartDefinition back_left_leg2 = back_left2.addOrReplaceChild("back_left_leg2", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition back_left_foot2 = back_left_leg2.addOrReplaceChild("back_left_foot2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 38).addBox(-3.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition back_right2 = body2.addOrReplaceChild("back_right2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -2.0F, 3.0F));

        PartDefinition back_right_leg2 = back_right2.addOrReplaceChild("back_right_leg2", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-2.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition back_right_foot3 = back_right_leg2.addOrReplaceChild("back_right_foot3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 38).mirror().addBox(-2.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition neck2 = body2.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(32, 12).addBox(-1.5F, -10.0F, -1.0F, 3.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(18, 43).addBox(0.0F, -10.0F, -3.0F, 0.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -17.0F, 0.5672F, 0.0F, 0.0F));

        PartDefinition head2 = neck2.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 12).addBox(-3.5F, -2.0F, -6.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(3.5F, -14.0F, -5.0F, 0.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).mirror().addBox(-3.5F, -14.0F, -5.0F, 0.0F, 12.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(-2.5F, -1.0F, -10.0F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(26, 72).addBox(2.0F, -4.0F, 1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 72).mirror().addBox(-4.0F, -4.0F, 1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -9.0F, 2.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition front_left2 = body2.addOrReplaceChild("front_left2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -2.0F, -14.0F));

        PartDefinition front_left_leg2 = front_left2.addOrReplaceChild("front_left_leg2", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition front_left_foot2 = front_left_leg2.addOrReplaceChild("front_left_foot2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 38).addBox(-3.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition front_right2 = body2.addOrReplaceChild("front_right2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -2.0F, -14.0F));

        PartDefinition front_right_leg2 = front_right2.addOrReplaceChild("front_right_leg2", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-2.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition front_right_foot2 = front_right_leg2.addOrReplaceChild("front_right_foot2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 38).mirror().addBox(-2.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition engine2 = body2.addOrReplaceChild("engine2", CubeListBuilder.create().texOffs(9, 46).addBox(-3.5F, -6.0F, -12.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));

        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 7).addBox(-4.5F, -8.0F, -17.0F, 9.0F, 9.0F, 22.0F, new CubeDeformation(0.05F))
                .texOffs(72, 0).addBox(-3.0F, -10.0F, -19.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-4.0F, -7.0F, 1.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, -17.0F, 6.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(43, 48).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 66).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 4.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition back_left = body.addOrReplaceChild("back_left", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -2.0F, 3.0F));

        PartDefinition back_left_leg = back_left.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition back_left_foot = back_left_leg.addOrReplaceChild("back_left_foot", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 38).addBox(-3.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition back_right = body.addOrReplaceChild("back_right", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -2.0F, 3.0F));

        PartDefinition back_right_leg = back_right.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-2.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition back_right_foot = back_right_leg.addOrReplaceChild("back_right_foot", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 38).mirror().addBox(-2.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(32, 12).addBox(-1.5F, -10.0F, -1.0F, 3.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(18, 43).addBox(0.0F, -10.0F, -3.0F, 0.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -17.0F, 0.5672F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-3.5F, -2.0F, -6.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(3.5F, -14.0F, -5.0F, 0.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).mirror().addBox(-3.5F, -14.0F, -5.0F, 0.0F, 12.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(-2.5F, -1.0F, -10.0F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(26, 72).addBox(2.0F, -4.0F, 1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 72).mirror().addBox(-4.0F, -4.0F, 1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -9.0F, 2.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition front_left = body.addOrReplaceChild("front_left", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -2.0F, -14.0F));

        PartDefinition front_left_leg = front_left.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition front_left_foot = front_left_leg.addOrReplaceChild("front_left_foot", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 38).addBox(-3.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition front_right = body.addOrReplaceChild("front_right", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -2.0F, -14.0F));

        PartDefinition front_right_leg = front_right.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-2.0F, 0.0F, -2.0F, 5.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition front_right_foot = front_right_leg.addOrReplaceChild("front_right_foot", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 38).mirror().addBox(-2.0F, 3.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition engine = body.addOrReplaceChild("engine", CubeListBuilder.create().texOffs(9, 46).addBox(-3.5F, -6.0F, -12.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void setupAnim(float pitch, float yaw, float ageInTicks, boolean walk) {
        bone.yRot=yaw;
        bone.xRot = pitch;

        front_left.y= Mth.sin(ageInTicks/5)-2;
        front_left.z= -Mth.cos(ageInTicks/5)-14;

        front_left_leg.xRot = Mth.sin(ageInTicks/5)*0.2f+0.5f;

        front_right.y= -Mth.sin(ageInTicks/5)-2;
        front_right.z= Mth.cos(ageInTicks/5)-14;

        front_right_leg.xRot = Mth.cos(ageInTicks/5)*0.2f+0.5f;

        back_left.y= -Mth.cos(ageInTicks/5)-2;
        back_left.z= Mth.sin(ageInTicks/5);

        back_left_leg.xRot = -Mth.cos(ageInTicks/5)*0.2f+0.5f;

        back_right.y= Mth.cos(ageInTicks/5)-2;
        back_right.z= -Mth.sin(ageInTicks/5);

        back_right_leg.xRot = Mth.sin(ageInTicks/5)*0.2f+0.5f;

        tail.xRot = Mth.sin(ageInTicks)*0.1f+0.5f;

        neck.xRot = Mth.sin(ageInTicks/5)*0.1f+0.7f;
        head.xRot = -neck.xRot;



        /* 2 */

        front_left2.y= Mth.sin(ageInTicks/5)-2;
        front_left2.z= -Mth.cos(ageInTicks/5)-14;

        front_left_leg2.xRot = Mth.sin(ageInTicks/5)*0.2f+0.5f;

        front_right2.y= -Mth.sin(ageInTicks/5)-2;
        front_right2.z= Mth.cos(ageInTicks/5)-14;

        front_right_leg2.xRot = Mth.cos(ageInTicks/5)*0.2f+0.5f;

        back_left2.y= -Mth.cos(ageInTicks/5)-2;
        back_left2.z= Mth.sin(ageInTicks/5);

        back_left_leg2.xRot = -Mth.cos(ageInTicks/5)*0.2f+0.5f;

        back_right2.y= Mth.cos(ageInTicks/5)-2;
        back_right2.z= -Mth.sin(ageInTicks/5);

        back_right_leg2.xRot = Mth.sin(ageInTicks/5)*0.2f+0.5f;

        tail2.xRot = Mth.sin(ageInTicks)*0.1f+0.5f;

        neck2.xRot = Mth.sin(ageInTicks/5)*0.1f+0.7f;
        head2.xRot = -neck2.xRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        bone.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
