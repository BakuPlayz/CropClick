package com.github.bakuplayz.cropclick.events;

import com.github.bakuplayz.cropclick.CropClick;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;


/**
 * A base class for all {@link CropClick} events.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class Event extends org.bukkit.event.Event {

    /**
     * A variable containing all the events related to {@link CropClick}.
     */
    private static final HandlerList HANDLERS = new HandlerList();


    /**
     * Gets all the {@link #HANDLERS event handlers}.
     *
     * @return the event handlers.
     */
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    /**
     * Gets all the {@link #HANDLERS event handlers}.
     *
     * @return the event handlers.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

}