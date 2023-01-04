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
     * Handles navigations back to the previous menu.
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