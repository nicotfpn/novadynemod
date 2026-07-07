package com.novadyne;

import com.novadyne.items.ExosuitTier1Item;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NovaDyneMod.MODID);

    public static final DeferredItem<ExosuitTier1Item> EXOSUIT_TIER1_HELMET = ITEMS.registerItem("exosuit_tier1_helmet",
            props -> new ExosuitTier1Item(ModArmorMaterials.EXOSUIT_TIER1, ArmorType.HELMET,
                    props.component(ModDataComponents.EXOSUIT_ENERGY.get(), 0L)));

    public static final DeferredItem<ExosuitTier1Item> EXOSUIT_TIER1_CHESTPLATE = ITEMS.registerItem("exosuit_tier1_chestplate",
            props -> new ExosuitTier1Item(ModArmorMaterials.EXOSUIT_TIER1, ArmorType.CHESTPLATE,
                    props.component(ModDataComponents.EXOSUIT_ENERGY.get(), 0L)));

    public static final DeferredItem<ExosuitTier1Item> EXOSUIT_TIER1_LEGGINGS = ITEMS.registerItem("exosuit_tier1_leggings",
            props -> new ExosuitTier1Item(ModArmorMaterials.EXOSUIT_TIER1, ArmorType.LEGGINGS,
                    props.component(ModDataComponents.EXOSUIT_ENERGY.get(), 0L)));

    public static final DeferredItem<ExosuitTier1Item> EXOSUIT_TIER1_BOOTS = ITEMS.registerItem("exosuit_tier1_boots",
            props -> new ExosuitTier1Item(ModArmorMaterials.EXOSUIT_TIER1, ArmorType.BOOTS,
                    props.component(ModDataComponents.EXOSUIT_ENERGY.get(), 0L)));

    public static final DeferredItem<Item> EXOSUIT_INGOT = ITEMS.registerItem("exosuit_ingot", Item::new);
}
