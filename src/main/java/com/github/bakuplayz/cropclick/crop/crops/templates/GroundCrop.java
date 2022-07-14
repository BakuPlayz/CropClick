package com.github.bakuplayz.cropclick.crop.crops.templates;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class GroundCrop extends BaseCrop {

    public int getCurrentAge(final @NotNull Block block) {
        return block.getData();
    }

}