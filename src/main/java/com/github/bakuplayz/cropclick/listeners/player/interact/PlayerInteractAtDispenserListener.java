package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtDispenserEvent;
import com.github.bakuplayz.cropclick.menu.menus.links.DispenserLinkMenu;
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
public final class PlayerInteractAtDispenserListener implements Listener {

    private final CropClick plugin;


    public PlayerInteractAtDispenserListener(@NotNull CropClick plugin) {
        this.plugin = plugin;
    }


    /**
     * If a player interacts with a dispenser, open a menu that allows them to interact with the dispenser.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtDispenser(@NotNull PlayerInteractAtDispenserEvent event) {
        if (event.isCancelled()) return;

        new DispenserLinkMenu(
                plugin,
                event.getPlayer(),
                event.getBlock(),
                false
        ).open();
    }

}