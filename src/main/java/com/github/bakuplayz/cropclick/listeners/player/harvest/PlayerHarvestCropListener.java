package com.github.bakuplayz.cropclick.listeners.player.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.player.harvest.PlayerHarvestCropEvent;
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
public final class PlayerHarvestCropListener implements Listener {

    private final CropManager cropManager;
    private final AddonManager addonManager;
    private final WorldManager worldManager;

    public PlayerHarvestCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithCrop(@NotNull PlayerInteractEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getClickedBlock();
        if (BlockUtil.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
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

        String cropName = crop.getName().toLowerCase();
        if (!PermissionUtil.canHarvestCrop(player, cropName)) {
            return;
        }

        Bukkit.getPluginManager().callEvent(new PlayerHarvestCropEvent(crop, block, player));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerHarvestCrop(@NotNull PlayerHarvestCropEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Crop crop = event.getCrop();

        if (!crop.hasDrops()) {
            event.setCancelled(true);
            return;
        }

        addonManager.applyEffects(player);

        // LATER: crop#playSounds();
        // LATER: crop#playEffects();
        if (crop.canHarvest(player)) {
            crop.harvest(player);
            crop.replant(block);
        }

    }

}