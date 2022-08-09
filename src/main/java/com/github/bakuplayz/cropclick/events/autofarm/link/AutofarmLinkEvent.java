package com.github.bakuplayz.cropclick.events.autofarm.link;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmLinkEvent extends Event implements Cancellable {

    private final @Getter Autofarm autofarm;

    private @Setter @Getter boolean cancelled;


    public AutofarmLinkEvent(@NotNull Autofarm autofarm) {
        this.autofarm = autofarm;
    }

}