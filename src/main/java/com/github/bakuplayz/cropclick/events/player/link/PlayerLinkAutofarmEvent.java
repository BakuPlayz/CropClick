package com.github.bakuplayz.cropclick.events.player.link;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Player} links a {@link Autofarm}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerLinkAutofarmEvent extends Event implements Cancellable {

    private final @Getter Player player;
    private final @Getter Autofarm autofarm;

    private @Setter @Getter boolean cancelled;


    public PlayerLinkAutofarmEvent(@NotNull Player player, @NotNull Autofarm autofarm) {
        this.autofarm = autofarm;
        this.player = player;
    }

}