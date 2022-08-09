package com.github.bakuplayz.cropclick.events.player.interact;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
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
public final class PlayerInteractAtContainerEvent extends Event implements Cancellable {

    private final @Getter Block block;
    private final @Getter Player player;
    private final @Getter Container container;

    private @Setter @Getter boolean cancelled;


    public PlayerInteractAtContainerEvent(@NotNull Player player, @NotNull Block block, Container container) {
        this.container = container;
        this.player = player;
        this.block = block;
    }

}