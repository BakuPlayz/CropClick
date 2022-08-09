package com.github.bakuplayz.cropclick.events.harvest;

import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public class HarvestCropEvent extends Event implements Cancellable {

    private final @Getter Crop crop;
    private final @Getter Block block;

    private @Setter @Getter boolean cancelled;


    public HarvestCropEvent(@NotNull Crop crop, @NotNull Block block) {
        this.block = block;
        this.crop = crop;
    }

}