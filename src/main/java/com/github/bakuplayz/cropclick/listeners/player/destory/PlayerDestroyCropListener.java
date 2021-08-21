package com.github.bakuplayz.cropclick.listeners.player.destory;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.events.player.destroy.PlayerDestroyCropEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerDestroyCropListener implements Listener {

    private final OfflineGrowthAddon offlineGrowthAddon;

    public PlayerDestroyCropListener(final @NotNull CropClick plugin) {
        this.offlineGrowthAddon = plugin.getAddonManager().getOfflineGrowthAddon();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithCrop(final @NotNull BlockBreakEvent event) {
        //call PlayerDestroyCropEvent
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDestroyCrop(final @NotNull PlayerDestroyCropEvent event) {
        //check for permissions.
        offlineGrowthAddon.removeCrop(event.getBlock().getLocation());
    }

}
