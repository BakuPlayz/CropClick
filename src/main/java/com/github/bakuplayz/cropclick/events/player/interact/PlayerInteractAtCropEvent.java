package com.github.bakuplayz.cropclick.events.player.interact;


import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Player} interact at a {@link Crop crop}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerInteractAtCropEvent extends Event implements Cancellable {

    private final @Getter Crop crop;
    private final @Getter Block block;
    private final @Getter Player player;

    /**
     * Checks whether the event is cancelled or not.
     */
    private @Setter @Getter boolean cancelled;


    public PlayerInteractAtCropEvent(@NotNull Player player, @NotNull Block block, Crop crop) {
        this.player = player;
        this.block = block;
        this.crop = crop;
    }

}