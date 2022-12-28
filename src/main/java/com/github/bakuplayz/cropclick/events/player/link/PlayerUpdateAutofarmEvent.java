package com.github.bakuplayz.cropclick.events.player.link;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Player} updates a {@link Autofarm}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerUpdateAutofarmEvent extends Event implements Cancellable {

    private final @Getter Player player;
    private final @Getter Autofarm oldAutofarm;
    private final @Getter Autofarm newAutofarm;

    private @Setter @Getter boolean cancelled;


    public PlayerUpdateAutofarmEvent(@NotNull Player player,
                                     @NotNull Autofarm oldAutofarm,
                                     @NotNull Autofarm newAutofarm) {
        this.oldAutofarm = oldAutofarm;
        this.newAutofarm = newAutofarm;
        this.player = player;
    }


}