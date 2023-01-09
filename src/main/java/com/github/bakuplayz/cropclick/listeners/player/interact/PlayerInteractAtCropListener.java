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

package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtCropEvent;
import com.github.bakuplayz.cropclick.menu.menus.links.CropLinkMenu;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * A listener handling all the {@link Crop Crop} interactions caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerInteractAtCropListener implements Listener {

    private final CropClick plugin;
    private final AutofarmManager autofarmManager;


    public PlayerInteractAtCropListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }


    /**
     * Handles all the {@link Player player} interact at {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtCrop(@NotNull PlayerInteractAtCropEvent event) {
        if (event.isCancelled()) return;

        Crop crop = event.getCrop();

        if (!crop.isHarvestable()) {
            event.setCancelled(true);
            return;
        }

        if (!crop.isLinkable()) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();
        Player player = event.getPlayer();
        Autofarm autofarm = autofarmManager.findAutofarm(block);

        if (autofarmManager.isUsable(autofarm)) {
            if (!PermissionUtils.canInteractAtOthersFarm(player, autofarm)) {
                event.setCancelled(true);
                return;
            }

            if (AutofarmUtils.hasCachedID(block)) {
                AutofarmUtils.addCachedID(plugin, autofarm);
            }
        }

        if (plugin.isDebugging()) {
            plugin.getLogger()
                  .info(String.format("%s (Player): Called the interact at crop event!", player.getName()));
        }

        new CropLinkMenu(
                plugin,
                player,
                block,
                autofarm
        ).openMenu();
    }

}