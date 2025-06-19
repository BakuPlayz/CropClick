/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.listeners.player.leave;

import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.Log.Tag;

/**
 * A listener handling all the {@link Player} leave events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class PlayerLeaveListener implements Listener {

    /**
     * Handles all the {@link Player player} leave events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Log.debug("{0}: Called the leave event.", Tag.PLAYER, player.getName());

        CropPlayer.getCache().remove(player.getUniqueId());
    }

}
