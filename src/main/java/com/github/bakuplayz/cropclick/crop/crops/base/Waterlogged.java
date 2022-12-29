package com.github.bakuplayz.cropclick.crop.crops.base;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * An interface representing waterlogged crops.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Waterlogged {

    /**
     * Checks if the block is waterlogged.
     *
     * @param block The block to check.
     *
     * @return true if waterlogged, otherwise false.
     */
    boolean isWaterLogged(@NotNull Block block);

    /**
     * Set the waterlogged state of the block to the provided waterlogged state.
     *
     * @param block       The block you want to set the waterlogged state of.
     * @param waterlogged The waterlogged state.
     */
    void setWaterLogged(@NotNull Block block, boolean waterlogged);

}