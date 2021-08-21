package com.github.bakuplayz.cropclick.events;

import org.bukkit.event.HandlerList;

public abstract class Event extends org.bukkit.event.Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
