package com.github.bakuplayz.cropclick.listeners.player.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.*;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.player.harvest.PlayerHarvestCropEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

    private final mcMMOAddon mcMMOAddon;
    private final TownyAddon townyAddon;
    private final ResidenceAddon residenceAddon;
    private final JobsRebornAddon jobsRebornAddon;
    private final WorldGuardAddon worldGuardAddon;

    public PlayerHarvestCropListener(final @NotNull CropClick plugin) {
        AddonManager addonManager = plugin.getAddonManager();
        this.jobsRebornAddon = addonManager.getJobsRebornAddon();
        this.worldGuardAddon = addonManager.getWorldGuardAddon();
        this.residenceAddon = addonManager.getResidenceAddon();
        this.mcMMOAddon = addonManager.getMcMMOAddon();
        this.townyAddon = addonManager.getTownyAddon();
        this.cropManager = plugin.getCropManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithCrop(final @NotNull PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) return;
        if (block.getType() == null) return;
        if (block.getType() == Material.AIR) return;

        Crop crop = cropManager.getCrop(block);
        if (crop == null) return;
        if (!crop.isEnabled()) return;
        if (crop.getDrops() == null) return;
        if (crop.getDropAmount() < 0) return;
        if (crop.getDropChance() < 0) return;
        if (crop.getHarvestAge() != crop.getCurrentAge(block)) return;

        Bukkit.getPluginManager().callEvent(new PlayerHarvestCropEvent(crop, block, event.getAction(), event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerHarvestCrop(final @NotNull PlayerHarvestCropEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Crop crop = event.getCrop();

        if (crop.getDropChance() > crop.getRandomDropChance()) return;

        if (townyAddon != null && townyAddon.isEnabled()) {
            if (action != Action.LEFT_CLICK_BLOCK) return;
            if (!townyAddon.canDestroyCrop(player)) return;
        }

        if (worldGuardAddon != null && worldGuardAddon.isEnabled()) {
            if (!worldGuardAddon.regionAllowsPlayer(player)) return;
        }

        if (residenceAddon != null && residenceAddon.isEnabled()) {
            if (!residenceAddon.regionOrPlayerHasFlag(player)) return;
        }

        if (jobsRebornAddon != null && jobsRebornAddon.isEnabled()) {
            jobsRebornAddon.updateStats(player);
        }

        if (mcMMOAddon != null && mcMMOAddon.isEnabled()) {
            mcMMOAddon.addExperience(player);
        }

        // LATER: crop#playSounds();
        // LATER: crop#playEffects();
        if (crop.hasHarvestPermission(player)) {
            crop.harvest(player.getInventory());
            crop.replant(block);
        }
    }
}