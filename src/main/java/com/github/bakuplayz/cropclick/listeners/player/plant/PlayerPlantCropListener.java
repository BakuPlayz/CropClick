package com.github.bakuplayz.cropclick.listeners.player.plant;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.player.plant.PlayerPlantCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.EventUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * A listener handling all the {@link Crop crop} plant events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerPlantCropListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final CropManager cropManager;
    private final AddonManager addonManager;
    private final WorldManager worldManager;

    private final OfflineGrowthAddon growthAddon;


    public PlayerPlantCropListener(@NotNull CropClick plugin) {
        this.logger = plugin.getLogger();
        this.isDebugging = plugin.isDebugging();
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.growthAddon = addonManager.getOfflineGrowthAddon();
    }


    /**
     * Handles all the {@link Player player} place {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceCrop(@NotNull BlockPlaceEvent event) {
        if (VersionUtils.between(12, 12.9) && !EventUtils.isMainHand(event.getHand())) {
            return;
        }

        Block block = event.getBlockPlaced();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!addonManager.canModifyRegion(player)) {
            return;
        }

        Crop crop = cropManager.findByBlock(block);
        if (crop == null) {
            return;
        }

        if (!PermissionUtils.canPlantCrop(player, crop.getName())) {
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new PlayerPlantCropEvent(crop, block, player)
        );
    }


    /**
     * Handles all the {@link Player player} plant {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlantCrop(@NotNull PlayerPlantCropEvent event) {
        if (event.isCancelled()) return;

        if (isDebugging) {
            logger.info(String.format("%s (Player): Called the plant crop event!", event.getPlayer().getName()));
        }

        if (addonManager.isInstalledAndEnabled(growthAddon)) {
            growthAddon.registerCrop(event.getBlock().getLocation());
        }
    }

}