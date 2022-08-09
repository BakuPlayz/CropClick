package com.github.bakuplayz.cropclick.events.player.interact;


import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
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
public final class PlayerInteractAtDispenserEvent extends Event implements Cancellable {

    private final @Getter Block block;
    private final @Getter Player player;
    private final @Getter Dispenser dispenser;

    private @Setter @Getter boolean cancelled;


    public PlayerInteractAtDispenserEvent(@NotNull Player player, @NotNull Dispenser dispenser) {
        this.block = dispenser.getBlock();
        this.dispenser = dispenser;
        this.player = player;
    }

}