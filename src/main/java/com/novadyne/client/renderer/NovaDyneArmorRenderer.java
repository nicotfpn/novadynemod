package com.novadyne.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.novadyne.ModDataComponents;
import com.novadyne.NovaDyneMod;
import com.novadyne.client.loader.OBJLoader;
import com.novadyne.client.model.OBJModel;
import com.novadyne.items.ExosuitTier1Item;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class NovaDyneArmorRenderer extends RenderLayer<AvatarRenderState, PlayerModel> {

    private static final String HELMET_PATH = "models/entity/helmet.obj";

    public NovaDyneArmorRenderer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       AvatarRenderState renderState, float limbSwing, float limbSwingAmount) {
        ItemStack chestStack = renderState.chestEquipment;
        if (!(chestStack.getItem() instanceof ExosuitTier1Item)) return;

        Identifier texture = getArmorTexture(chestStack);
        OBJModel helmetModel = OBJLoader.getModel(HELMET_PATH);
        if (helmetModel == null) return;

        PlayerModel model = getParentModel();
        OrderedSubmitNodeCollector ordered = collector.order(0);
        int overlay = OverlayTexture.NO_OVERLAY;

        poseStack.pushPose();
        model.head.translateAndRotate(poseStack);
        ordered.submitCustomGeometry(poseStack, RenderTypes.armorCutoutNoCull(texture),
                (pose, consumer) -> helmetModel.render(pose, consumer, packedLight, overlay, 1.0F, 1.0F, 1.0F, 1.0F));
        poseStack.popPose();

        poseStack.pushPose();
        model.body.translateAndRotate(poseStack);
        ordered.submitCustomGeometry(poseStack, RenderTypes.armorCutoutNoCull(texture),
                (pose, consumer) -> helmetModel.render(pose, consumer, packedLight, overlay, 1.0F, 1.0F, 1.0F, 1.0F));
        poseStack.popPose();
    }

    private static Identifier getArmorTexture(ItemStack stack) {
        long energy = stack.getOrDefault(ModDataComponents.EXOSUIT_ENERGY.get(), 0L);
        if (energy > 0) {
            return Identifier.fromNamespaceAndPath(NovaDyneMod.MODID, "textures/armor/exosuit_tier1.png");
        }
        return Identifier.fromNamespaceAndPath(NovaDyneMod.MODID, "textures/armor/exosuit_tier1_unpowered.png");
    }
}
