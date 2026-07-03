package com.novadyne.items;

import com.novadyne.ModArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;

public class ExosuitTier1Item extends Item {

    public ExosuitTier1Item(Item.Properties properties) {
        super(properties.humanoidArmor(ModArmorMaterials.EXOSUIT_TIER1, ArmorType.CHESTPLATE));
    }
}
