package com.github.bakuplayz.cropclick.listeners.player.plant;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.player.plant.PlayerPlantCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
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
 * @version 2.0.0
 * @since 2.0.0
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


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceCrop(@NotNull PlayerInteractEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getClickedBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        if (!BlockUtils.isPlantableSurface(block)) {
            return;
        }

        Player player = event.getPlayer();
        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        // TODO: Check for the #getMaterial and then validate for placing...

        Crop crop = cropManager.findByBlock(block);
        if (!cropManager.validate(crop, block)) {
            return;
        }

        if (!PermissionUtils.canPlantCrop(player, crop.getName())) {
            return;
        }

        // Maybe needs to cache a plant for some time...

        Bukkit.getPluginManager().callEvent(new PlayerPlantCropEvent(crop, block, player));
    }


    /**
     * If the growth addon is enabled, add the crop to the growth addon's list of crops.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlantCrop(@NotNull PlayerPlantCropEvent event) {
        if (event.isCancelled()) return;

        if (addonManager.isPresentAndEnabled(growthAddon)) {
            growthAddon.addCrop(event.getBlock().getLocation());
        }
    }

}