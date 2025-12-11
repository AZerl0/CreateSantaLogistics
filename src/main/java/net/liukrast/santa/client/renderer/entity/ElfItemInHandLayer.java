package net.liukrast.santa.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.liukrast.santa.client.model.RoboElfModel;
import net.liukrast.santa.world.entity.RoboElf;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ElfItemInHandLayer extends ItemInHandLayer<RoboElf, RoboElfModel> {
    public ElfItemInHandLayer(RenderLayerParent<RoboElf, RoboElfModel> renderer, ItemInHandRenderer itemInHandRenderer) {
        super(renderer, itemInHandRenderer);
    }

    @Override
    protected void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.renderArmWithItem(livingEntity, itemStack, ItemDisplayContext.FIXED, arm, poseStack, buffer, packedLight);
    }
}
