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

package com.github.bakuplayz.cropclick.listeners;

import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;


/**
 * A listener handling all the {@link Menu} events.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MenuListener implements Listener {


    /**
     * Handles all {@link Menu menu} click events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onMenuClick(@NotNull InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof BaseMenu) {
            if (event.getCurrentItem() == null) {
                event.setCancelled(true);
                return;
            }

            if (event.getRawSlot() == -999) {
                event.getWhoClicked().closeInventory();
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);

            ((Menu) holder).handleMenu(event);
        }
    }


    /**
     * Handles all {@link Menu menu} drag events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onMenuDrag(@NotNull InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            event.setCancelled(true);
        }
    }

}