package com.novadyne;

import java.util.Map;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.tags.ItemTags;

public class ModArmorMaterials {
    public static final ResourceKey<EquipmentAsset> EXOSUIT_TIER1_ASSET =
            ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(NovaDyneMod.MODID, "exosuit_tier1"));

    public static final ArmorMaterial EXOSUIT_TIER1 = new ArmorMaterial(
            33,
            Map.of(
                    ArmorType.HELMET, 3,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),
            15,
            SoundEvents.ARMOR_EQUIP_IRON,
            2.0F,
            0.1F,
            ItemTags.REPAIRS_IRON_ARMOR,
            EXOSUIT_TIER1_ASSET
    );
}
