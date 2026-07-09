package com.novadyne;

import com.novadyne.common.block.LitografiaBlock;
import com.novadyne.common.block.MaceratorBlock;
import com.novadyne.common.block.ProcessorBlock;
import com.novadyne.common.block.WaferPressBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NovaDyneMod.MODID);

    public static final DeferredBlock<MaceratorBlock> MACERATOR =
            BLOCKS.register("macerator", () -> new MaceratorBlock(
                    BlockBehaviour.Properties.of().strength(3.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<WaferPressBlock> WAFER_PRESS =
            BLOCKS.register("wafer_press", () -> new WaferPressBlock(
                    BlockBehaviour.Properties.of().strength(3.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<ProcessorBlock> PROCESSOR =
            BLOCKS.register("processor", () -> new ProcessorBlock(
                    BlockBehaviour.Properties.of().strength(3.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<LitografiaBlock> LITOGRAFIA =
            BLOCKS.register("litografia", () -> new LitografiaBlock(
                    BlockBehaviour.Properties.of().strength(3.5F, 6.0F).requiresCorrectToolForDrops()));

    private ModBlocks() {}
}
