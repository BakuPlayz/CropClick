package com.github.bakuplayz.cropclick.listeners.player.plant;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.player.plant.PlayerPlantCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import com.github.bakuplayz.cropclick.utils.PermissionUtil;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class PlayerPlantCropListener implements Listener {

    private final CropManager cropManager;
    private final AddonManager addonManager;
    private final WorldManager worldManager;

    private final OfflineGrowthAddon growthAddon;

    public PlayerPlantCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.growthAddon = addonManager.getOfflineGrowthAddon();
    }

    /* Step by step:
     * 1. isAir,
     * 2. hasPermission,
     * 3. hasWorldBanished,
     * 4. canModify,
     * 5. isValid
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceCrop(@NotNull PlayerInteractEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getClickedBlock();
        if (BlockUtil.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
        if (!PermissionUtil.canPlaceCrop(player)) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessable(world)) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        Crop crop = cropManager.findByBlock(block);
        if (!cropManager.validate(crop, block)) {
            return;
        }

        // Maybe needs to cache a plant for some time...

        Bukkit.getPluginManager().callEvent(new PlayerPlantCropEvent(crop, block, player));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlantCrop(@NotNull PlayerPlantCropEvent event) {
        if (event.isCancelled()) return;

        if (addonManager.isPresent(growthAddon)) {
            growthAddon.addCrop(event.getBlock().getLocation());
        }

    }

}