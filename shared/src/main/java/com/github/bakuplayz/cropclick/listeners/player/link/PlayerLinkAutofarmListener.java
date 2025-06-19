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

package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmLinkEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.Log.Tag;
import static com.github.bakuplayz.cropclick.common.Languages.Menu.LINK_ACTION_SUCCESS;


/**
 * A listener handling all the {@link Autofarm} linking events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerLinkAutofarmListener implements Listener {

    private final CropClick plugin;


    public PlayerLinkAutofarmListener(@NotNull CropClick plugin) {
        this.plugin = plugin;
    }


    /**
     * Handles all the {@link Player player} link {@link Autofarm autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLinkAutofarm(@NotNull PlayerLinkAutofarmEvent event) {
        CropPlayer player = event.getPlayer();

        LINK_ACTION_SUCCESS.send(plugin, player);

        Log.debug("{0}: Called the link event.", Tag.PLAYER, player.getOfflinePlayer().getName());

        Bukkit.getPluginManager().callEvent(
                new AutofarmLinkEvent(event.getAutofarm())
        );
    }

}