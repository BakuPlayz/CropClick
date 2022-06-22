package com.github.bakuplayz.cropclick.listeners.player.plant;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.player.plant.PlayerPlantCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerPlantCropListener implements Listener {

    private final CropManager cropManager;
    private final OfflineGrowthAddon offlineGrowthAddon;

    public PlayerPlantCropListener(final @NotNull CropClick plugin) {
        this.offlineGrowthAddon = plugin.getAddonManager().getOfflineGrowthAddon();
        this.cropManager = plugin.getCropManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceCrop(final @NotNull PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (BlockUtil.isAir(block)) return;

        Crop crop = cropManager.getCrop(block);
        if (!cropManager.isCropValid(crop, block)) return;

        //needs to check weather or not it's a new crop planted.

        Bukkit.getPluginManager().callEvent(new PlayerPlantCropEvent(block, event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlantCrop(final @NotNull PlayerPlantCropEvent event) {
        if (offlineGrowthAddon != null && offlineGrowthAddon.isEnabled()) {
            offlineGrowthAddon.addCrop(event.getBlock().getLocation());
        }
    }

}