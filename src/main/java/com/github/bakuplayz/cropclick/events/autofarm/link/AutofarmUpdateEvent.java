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
public final class AutofarmUpdateEvent extends Event implements Cancellable {

    private @Setter @Getter boolean cancelled;

    private final @Getter Autofarm oldAutofarm;
    private final @Getter Autofarm newAutofarm;


    public AutofarmUpdateEvent(@NotNull Autofarm oldAutofarm, @NotNull Autofarm newAutofarm) {
        this.oldAutofarm = oldAutofarm;
        this.newAutofarm = newAutofarm;
    }

}