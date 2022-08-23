package com.github.bakuplayz.cropclick.crop.crops.base;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface WaterloggedCrop {

    /**
     * Returns true if the block is waterlogged.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    boolean isWaterLogged(@NotNull Block block);
    
    /**
     * Set the waterlogged state of the block to the provided waterlogged state.
     *
     * @param block       The block you want to set the waterlogged state of.
     * @param waterlogged Whether the dripleaf is waterlogged.
     */
    void setWaterLogged(@NotNull Block block, boolean waterlogged);

}