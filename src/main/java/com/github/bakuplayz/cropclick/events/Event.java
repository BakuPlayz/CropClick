package com.github.bakuplayz.cropclick.events;

import org.bukkit.event.HandlerList;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class Event extends org.bukkit.event.Event {

    private static final HandlerList HANDLERS = new HandlerList();


    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}