package com.github.bakuplayz.cropclick.events.player.interact;


import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PlayerInteractAtCropEvent extends Event implements Cancellable {

    private final @Getter Crop crop;
    private final @Getter Player player;

    private @Setter @Getter boolean cancelled;

    public PlayerInteractAtCropEvent(@NotNull Player player, Crop crop) {
        this.player = player;
        this.crop = crop;
    }

}
