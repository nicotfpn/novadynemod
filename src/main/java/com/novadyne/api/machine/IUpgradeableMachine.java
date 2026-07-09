package com.novadyne.api.machine;

import net.minecraft.world.item.ItemStack;

public interface IUpgradeableMachine {
    int getValveTier();

    void setValveTier(int tier);

    boolean canAcceptValve(ItemStack stack);

    default boolean acceptsUpgradeSlot() {
        return true;
    }
}
