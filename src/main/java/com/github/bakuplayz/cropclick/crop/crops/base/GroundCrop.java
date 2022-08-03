package com.github.bakuplayz.cropclick.crop.crops.base;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Crop
 * @since 1.6.0
 */
public abstract class GroundCrop extends BaseCrop {

    public int getCurrentAge(@NotNull Block block) {
        return block.getData();
    }

}