package com.novadyne;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NovaDyneMod.MODID);

    // Materials
    public static final DeferredItem<Item> PURE_SILICON = ITEMS.register("pure_silicon",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PART_SILICON_WAFER = ITEMS.register("part_silicon_wafer",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CERAMIC_POWDER = ITEMS.register("ceramic_powder",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PART_COPPER_LAYER = ITEMS.register("part_copper_layer",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PART_BASE_WAFER = ITEMS.register("part_base_wafer",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STACKED_ELECTRONIC_CIRCUIT = ITEMS.register("stacked_electronic_circuit",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PART_ELECTRONIC_DIRTY_SILICON_WAFER = ITEMS.register("part_electronic_dirty_silicon_wafer",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PART_ELECTRONIC_FAILED_SILICON_WAFER = ITEMS.register("part_electronic_failed_silicon_wafer",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PART_ELECTRONIC_ETCHED_SILICON_WAFER = ITEMS.register("part_electronic_etched_silicon_wafer",
            () -> new Item(new Item.Properties()));

    // Valve upgrades
    public static final DeferredItem<Item> VALVE_TIER_1 = ITEMS.register("valve_tier_1",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VALVE_TIER_2 = ITEMS.register("valve_tier_2",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VALVE_TIER_3 = ITEMS.register("valve_tier_3",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VALVE_TIER_4 = ITEMS.register("valve_tier_4",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VALVE_TIER_5 = ITEMS.register("valve_tier_5",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VALVE_TIER_6 = ITEMS.register("valve_tier_6",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VALVE_TIER_7 = ITEMS.register("valve_tier_7",
            () -> new Item(new Item.Properties()));

    private ModItems() {}
}
