package com.github.bakuplayz.cropclick.events.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
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
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AutofarmHarvestCropEvent extends Event implements Cancellable {

    private final @Getter Crop crop;
    private final @Getter Block block;
    private final @Getter Autofarm autofarm;

    private @Setter @Getter boolean cancelled;


    public AutofarmHarvestCropEvent(@NotNull Crop crop,
                                    @NotNull Block block,
                                    @NotNull Autofarm autofarm) {
        this.autofarm = autofarm;
        this.block = block;
        this.crop = crop;
    }

}