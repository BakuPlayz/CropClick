/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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