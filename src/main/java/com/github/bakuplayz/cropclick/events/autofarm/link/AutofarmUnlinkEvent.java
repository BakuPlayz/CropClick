package com.github.bakuplayz.cropclick.events.autofarm.link;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Autofarm} unlinks.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmUnlinkEvent extends Event implements Cancellable {

    private final @Getter Autofarm autofarm;

    /**
     * Checks whether the event is cancelled or not.
     */
    private @Setter @Getter boolean cancelled;


    public AutofarmUnlinkEvent(@NotNull Autofarm autofarm) {
        this.autofarm = autofarm;
    }

}