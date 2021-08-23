package com.github.bakuplayz.cropclick.events.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.HarvestCropEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class AutofarmHarvestCropEvent extends HarvestCropEvent implements Cancellable {

    private final @Getter Autofarm autofarm;

    private @Setter @Getter boolean cancelled;

    public AutofarmHarvestCropEvent(@NotNull final Crop crop,
                                    @NotNull final Block block,
                                    @NotNull final Autofarm autofarm) {
        super(crop, block);
        this.autofarm = autofarm;
    }
}
