package com.novadyne;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.novadyne.common.integration.energy.NovadyneCapabilities;
import com.novadyne.items.ExosuitTier1Item;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@Mod(NovaDyneMod.MODID)
public class NovaDyneMod {
    public static final String MODID = "novadyne";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final float ABSORPTION_RATIO = 0.5f;
    private static final long ENERGY_PER_DAMAGE_POINT = 100L;
    public static final long EXOSUIT_TIER1_MAX_ENERGY = 750L;

    public NovaDyneMod(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
        modEventBus.addListener(NovadyneCapabilities::onRegisterCapabilities);
        NeoForge.EVENT_BUS.addListener(NovaDyneMod::onLivingIncomingDamage);
    }

    private static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // Check all armor slots for exosuit pieces
        long totalEnergy = 0;
        EquipmentSlot[] armorSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot slot : armorSlots) {
            var stack = player.getItemBySlot(slot);
            if (stack.getItem() instanceof ExosuitTier1Item) {
                totalEnergy += stack.getOrDefault(ModDataComponents.EXOSUIT_ENERGY.get(), 0L);
            }
        }
        if (totalEnergy <= 0) return;

        long capturedTotalEnergy = totalEnergy;
        event.addReductionModifier(net.neoforged.neoforge.common.damagesource.DamageContainer.Reduction.ARMOR, (container, baseReduction) -> {
            float damage = container.getNewDamage();
            if (damage <= 0) return baseReduction;

            // Calculate max damage that can be absorbed based on total energy
            float maxAbsorbDamageByEnergy = (float) capturedTotalEnergy / ENERGY_PER_DAMAGE_POINT;
            // Calculate proportional absorption (50% of damage)
            float proportionalAbsorb = damage * ABSORPTION_RATIO;
            // Actual absorbed damage is the minimum of the two
            float actualAbsorb = Math.min(maxAbsorbDamageByEnergy, proportionalAbsorb);
            // Energy to consume (100 per point of damage absorbed)
            long energyToConsume = (long) Math.round(actualAbsorb * ENERGY_PER_DAMAGE_POINT);

            // Distribute energy drain across all equipped exosuit pieces
            long remainingToDrain = energyToConsume;
            for (EquipmentSlot slot : armorSlots) {
                if (remainingToDrain <= 0) break;
                var stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof ExosuitTier1Item) {
                    long slotEnergy = stack.getOrDefault(ModDataComponents.EXOSUIT_ENERGY.get(), 0L);
                    long drainFromSlot = Math.min(remainingToDrain, slotEnergy);
                    stack.set(ModDataComponents.EXOSUIT_ENERGY.get(), slotEnergy - drainFromSlot);
                    remainingToDrain -= drainFromSlot;
                }
            }

            return baseReduction + actualAbsorb;
        });
    }
}