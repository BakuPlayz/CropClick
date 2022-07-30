package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtContainerEvent;
import com.github.bakuplayz.cropclick.menu.menus.interacts.ContainerInteractMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PlayerInteractAtContainerListener implements Listener {

    private final CropClick plugin;


    public PlayerInteractAtContainerListener(@NotNull CropClick plugin) {
        this.plugin = plugin;
    }


    /**
     * If a player interacts with a container, open a menu for them.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtContainer(@NotNull PlayerInteractAtContainerEvent event) {
        if (event.isCancelled()) return;

        new ContainerInteractMenu(plugin, event.getPlayer(), event.getBlock()).open();
    }

}