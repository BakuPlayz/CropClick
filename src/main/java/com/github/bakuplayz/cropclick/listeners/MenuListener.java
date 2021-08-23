package com.github.bakuplayz.cropclick.listeners;

import com.github.bakuplayz.cropclick.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class MenuListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onMenuClick(final @NotNull InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) {
            if (event.getCurrentItem() == null) {
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

    @EventHandler(priority = EventPriority.LOW)
    public void onMenuDrag(final @NotNull InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            event.setCancelled(true);
        }
    }
}
