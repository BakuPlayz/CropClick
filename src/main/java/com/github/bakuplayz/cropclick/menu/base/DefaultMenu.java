package com.github.bakuplayz.cropclick.menu.base;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * An interface acting as a base for a menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface DefaultMenu {

    /**
     * It sets the menu items.
     */
    void setMenuItems();

    /**
     * This function is called when a player clicks on a menu item.
     *
     * @param event The InventoryClickEvent that was fired.
     */
    void handleMenu(@NotNull InventoryClickEvent event);

    /**
     * If the item clicked is the back item, open the passed menu.
     *
     * @param clicked The item that was clicked.
     * @param menu    The menu that the item is in.
     */
    void handleBack(@NotNull ItemStack clicked, @NotNull Menu menu);

    /**
     * This function opens the menu for the player.
     */
    void open();


    /**
     * It refreshes the menu.
     */
    void refresh();

}