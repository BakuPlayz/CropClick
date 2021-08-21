package com.github.bakuplayz.cropclick.events;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public abstract class HarvestCropEvent extends Event {

    protected final @Getter Crop crop;
    protected final @Getter Block block;

    public HarvestCropEvent(final @NotNull Crop crop,
                            final @NotNull Block block) {
        this.block = block;
        this.crop = crop;
    }
}
