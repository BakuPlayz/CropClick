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

package com.github.bakuplayz.cropclick.listeners.player.join;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * A listener handling all the {@link Player} join events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerJoinListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final UpdateManager updateManager;


    public PlayerJoinListener(@NotNull CropClick plugin) {
        this.updateManager = plugin.getUpdateManager();
        this.isDebugging = plugin.isDebugging();
        this.logger = plugin.getLogger();
    }


    /**
     * Handles all the {@link Player operator} join events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onOperatorJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.isOp()) {
            return;
        }

        if (isDebugging) {
            logger.info(String.format("%s (Operator): Called the join event!", player.getName()));
        }

        updateManager.sendAlert(player);
    }

}