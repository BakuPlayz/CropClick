package com.github.bakuplayz.cropclick.listeners;

import com.github.bakuplayz.cropclick.menu.base.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;


/**
 * Represents the listener of all the registered menus.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MenuListener implements Listener {

    /**
     * If the inventory holder is a Menu, cancel the event and call the handleMenu function.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onMenuClick(@NotNull InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Menu) {
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
     * If the inventory that the event is happening in is a Menu, cancel the event.
     *
     * @param event The event that is being called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onMenuDrag(@NotNull InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            event.setCancelled(true);
        }
    }

}