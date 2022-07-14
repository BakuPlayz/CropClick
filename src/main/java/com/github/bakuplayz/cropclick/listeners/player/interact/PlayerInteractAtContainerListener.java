package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtContainerEvent;
import com.github.bakuplayz.cropclick.menu.menus.interacts.ContainerMenu;
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


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtContainer(@NotNull PlayerInteractAtContainerEvent event) {
        if (event.isCancelled()) return;

        new ContainerMenu(event.getPlayer(), event.getContainer(), event.getBlock(), plugin).open();
    }

}
