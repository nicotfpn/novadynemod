package com.novadyne.common.integration.energy;

import com.novadyne.ModItems;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public final class NovadyneCapabilities {
    private NovadyneCapabilities() {}

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        registerItemEnergy(event, ModItems.EXOSUIT_TIER1_HELMET.get(),
                stack -> new ExosuitEnergyItemHandler(stack, com.novadyne.NovaDyneMod.EXOSUIT_TIER1_MAX_ENERGY));
        registerItemEnergy(event, ModItems.EXOSUIT_TIER1_CHESTPLATE.get(),
                stack -> new ExosuitEnergyItemHandler(stack, com.novadyne.NovaDyneMod.EXOSUIT_TIER1_MAX_ENERGY));
        registerItemEnergy(event, ModItems.EXOSUIT_TIER1_LEGGINGS.get(),
                stack -> new ExosuitEnergyItemHandler(stack, com.novadyne.NovaDyneMod.EXOSUIT_TIER1_MAX_ENERGY));
        registerItemEnergy(event, ModItems.EXOSUIT_TIER1_BOOTS.get(),
                stack -> new ExosuitEnergyItemHandler(stack, com.novadyne.NovaDyneMod.EXOSUIT_TIER1_MAX_ENERGY));
    }

    public static void registerBlockEntityEnergy(RegisterCapabilitiesEvent event,
          net.minecraft.world.level.block.entity.BlockEntityType<?> beType,
          java.util.function.Function<net.minecraft.world.level.block.entity.BlockEntity, com.novadyne.api.energy.IStrictEnergyHandler> provider) {
        event.registerBlockEntity(Capabilities.Energy.BLOCK, beType,
              (be, direction) -> EnergyCompat.wrap(provider.apply(be)));
    }

    public static void registerItemEnergy(RegisterCapabilitiesEvent event,
          net.minecraft.world.level.ItemLike item,
          java.util.function.Function<net.minecraft.world.item.ItemStack, com.novadyne.api.energy.IStrictEnergyHandler> provider) {
        event.registerItem(Capabilities.Energy.ITEM, (stack, ctx) -> EnergyCompat.wrap(provider.apply(stack)), item);
    }
}
