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

package com.github.bakuplayz.cropclick.menu.base;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * An interface acting as a base for menus.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Menu {

    /**
     * Sets the implementing objects menu items.
     */
    void setMenuItems();

    /**
     * Handles all the menu click events.
     *
     * @param event the menu event.
     */
    void handleMenu(@NotNull InventoryClickEvent event);

    /**
     * Handles navigation back to the previous menu.
     *
     * @param clicked the item that was clicked.
     * @param menu    the menu to navigate back to.
     */
    void handleBack(@NotNull ItemStack clicked, @NotNull Menu menu);

    /**
     * Opens the implementing menu.
     */
    void openMenu();

    /**
     * Refresh the implementing menu.
     */
    void refreshMenu();

}