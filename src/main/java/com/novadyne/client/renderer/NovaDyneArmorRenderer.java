package com.novadyne.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

import java.util.Map;

public class NovaDyneArmorRenderer extends RenderLayer<AvatarRenderState, PlayerModel> {

    private static final String MODEL_PATH = "models/entity/exosuit_tier1.obj";

    private enum Part { BODY, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG, BOOTS_LEFT, BOOTS_RIGHT }

    private OBJModel currentModel;
    private int[] currentRange = new int[2];
    private int currentLight;
    private int currentOverlay;

    public NovaDyneArmorRenderer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
        super(renderer);
        NovaDyneMod.LOGGER.debug("NovaDyneArmorRenderer initialized");
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       AvatarRenderState renderState, float limbSwing, float limbSwingAmount) {
        // Check all 4 equipment slots for any exosuit piece
        ItemStack stack = findExosuitPiece(renderState);
        if (stack == null || stack.isEmpty()) return;

        if (!(stack.getItem() instanceof ExosuitTier1Item)) return;

        NovaDyneMod.LOGGER.debug("Rendering exosuit armor for player");

        Identifier texture = getArmorTexture(stack);
        OBJModel model = OBJLoader.getModel(MODEL_PATH);
        Map<String, int[]> ranges = OBJLoader.getObjectRanges(MODEL_PATH);
        if (model == null || ranges == null) {
            NovaDyneMod.LOGGER.error("OBJ model or ranges null for path: {}", MODEL_PATH);
            return;
        }

        NovaDyneMod.LOGGER.debug("OBJ model loaded, {} object ranges found", ranges.size());

        currentModel = model;
        currentLight = packedLight;
        currentOverlay = OverlayTexture.NO_OVERLAY;

        PlayerModel playerModel = getParentModel();
        OrderedSubmitNodeCollector ordered = collector.order(0);

        for (Map.Entry<String, int[]> entry : ranges.entrySet()) {
            String name = entry.getKey();
            int[] range = entry.getValue();
            if (range == null || range.length < 2) continue;

            Part part = getPart(name);
            if (part == null) continue;

            currentRange[0] = range[0];
            currentRange[1] = range[1];

            poseStack.pushPose();
            applyTransform(poseStack, playerModel, part);
            ordered.submitCustomGeometry(poseStack, RenderTypes.armorCutoutNoCull(texture),
                    this::renderCurrentRange);
            poseStack.popPose();
        }
    }

    private static ItemStack findExosuitPiece(AvatarRenderState state) {
        for (ItemStack s : new ItemStack[]{
                state.headEquipment,
                state.chestEquipment,
                state.legsEquipment,
                state.feetEquipment
        }) {
            if (!s.isEmpty() && s.getItem() instanceof ExosuitTier1Item) {
                return s;
            }
        }
        return null;
    }

    private void renderCurrentRange(PoseStack.Pose pose, VertexConsumer consumer) {
        currentModel.renderRange(pose, consumer, currentRange[0], currentRange[1], currentLight, currentOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static Part getPart(String name) {
        if (name.startsWith("boots_left")) {
            return Part.BOOTS_LEFT;
        }
        if (name.startsWith("boots_right")) {
            return Part.BOOTS_RIGHT;
        }
        if (name.startsWith("leggings_left") || name.startsWith("shared_boots_leggings_left")) {
            return Part.LEFT_LEG;
        }
        if (name.startsWith("leggings_right") || name.startsWith("shared_boots_leggings_right")) {
            return Part.RIGHT_LEG;
        }
        if (name.startsWith("chest_left_arm_")) {
            return Part.LEFT_ARM;
        }
        if (name.startsWith("chest_right_arm_")) {
            return Part.RIGHT_ARM;
        }
        if (name.startsWith("chest_body_") || name.startsWith("chest_")) {
            return Part.BODY;
        }
        if (name.startsWith("shared_chest_leggings")) {
            return Part.BODY;
        }
        return null;
    }

    private static void applyTransform(PoseStack poseStack, PlayerModel model, Part part) {
        switch (part) {
            case BODY:
                model.body.translateAndRotate(poseStack);
                break;
            case LEFT_ARM:
                model.leftArm.translateAndRotate(poseStack);
                break;
            case RIGHT_ARM:
                model.rightArm.translateAndRotate(poseStack);
                break;
            case LEFT_LEG:
                model.leftLeg.translateAndRotate(poseStack);
                break;
            case RIGHT_LEG:
                model.rightLeg.translateAndRotate(poseStack);
                break;
            case BOOTS_LEFT:
                model.leftLeg.translateAndRotate(poseStack);
                poseStack.translate(0.0D, -0.25D, 0.0D);
                break;
            case BOOTS_RIGHT:
                model.rightLeg.translateAndRotate(poseStack);
                poseStack.translate(0.0D, -0.25D, 0.0D);
                break;
        }
    }

    private static Identifier getArmorTexture(ItemStack stack) {
        long energy = stack.getOrDefault(ModDataComponents.EXOSUIT_ENERGY.get(), 0L);
        if (energy > 0) {
            return Identifier.fromNamespaceAndPath(NovaDyneMod.MODID, "textures/armor/exosuit_tier1.png");
        }
        return Identifier.fromNamespaceAndPath(NovaDyneMod.MODID, "textures/armor/exosuit_tier1_unpowered.png");
    }
}
