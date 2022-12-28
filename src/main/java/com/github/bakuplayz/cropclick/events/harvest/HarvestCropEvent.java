package com.github.bakuplayz.cropclick.events.harvest;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Player} or {@link Autofarm} harvests a {@link BaseCrop crop}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class HarvestCropEvent extends Event implements Cancellable {

    private final @Getter BaseCrop crop;
    private final @Getter Block block;

    private @Setter @Getter boolean cancelled;


    public HarvestCropEvent(@NotNull BaseCrop crop, @NotNull Block block) {
        this.block = block;
        this.crop = crop;
    }

}