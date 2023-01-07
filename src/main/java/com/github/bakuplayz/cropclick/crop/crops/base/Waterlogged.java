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
     * Checks whether the {@link Block provided block} is waterlogged.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    boolean isWaterLogged(@NotNull Block block);

    /**
     * Sets the waterlogged state of the {@link Block provided block} to the provided state.
     *
     * @param block       the block to set the state of.
     * @param waterlogged the state to set.
     */
    void setWaterLogged(@NotNull Block block, boolean waterlogged);

}