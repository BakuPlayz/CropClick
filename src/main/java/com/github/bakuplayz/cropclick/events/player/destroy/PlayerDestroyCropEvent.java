package com.github.bakuplayz.cropclick.events.player.destroy;

import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerDestroyCropEvent extends Event implements Cancellable {

    private final @Getter BaseCrop crop;
    private final @Getter Block block;
    private final @Getter Player player;

    private @Getter @Setter boolean cancelled;


    public PlayerDestroyCropEvent(@NotNull BaseCrop crop, @NotNull Block block, @NotNull Player player) {
        this.player = player;
        this.block = block;
        this.crop = crop;
    }

}