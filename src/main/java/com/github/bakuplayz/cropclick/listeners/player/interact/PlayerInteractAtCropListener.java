package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtCropEvent;
import com.github.bakuplayz.cropclick.menu.menus.interacts.CropInteractMenu;
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
public final class PlayerInteractAtCropListener implements Listener {

    private final CropClick plugin;


    public PlayerInteractAtCropListener(@NotNull CropClick plugin) {
        this.plugin = plugin;
    }


    /**
     * When a player interacts with a crop, open a menu that allows them to harvest the crop.
     *
     * @param event The event that was called.
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

        new CropInteractMenu(plugin, event.getPlayer(), event.getBlock()).open();
    }

}