package com.github.bakuplayz.cropclick.listeners.player.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.events.player.harvest.PlayerHarvestCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.EventUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerHarvestCropListener implements Listener {

    private final CropManager cropManager;
    private final AddonManager addonManager;
    private final WorldManager worldManager;

    private final HashMap<Crop, Long> harvestedCrops;


    public PlayerHarvestCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.harvestedCrops = cropManager.getHarvestedCrops();
    }


    /**
     * If the player is allowed to harvest the crop, then call the PlayerHarvestCropEvent.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithCrop(@NotNull PlayerInteractEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getClickedBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Action action = event.getAction();
        if (!EventUtils.isRightClick(action)) {
            return;
        }

        Player player = event.getPlayer();
        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!world.allowsPlayers()) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        Crop crop = cropManager.findByBlock(block);
        if (!cropManager.validate(crop, block)) {
            return;
        }

        if (!PermissionUtils.canHarvestCrop(player, crop.getName())) {
            return;
        }

        if (!crop.isHarvestable()) {
            return;
        }

        if (!crop.isHarvestAge(block)) {
            return;
        }

        if (harvestedCrops.containsKey(crop)) {
            return;
        }

        harvestedCrops.put(crop, System.nanoTime());

        Bukkit.getPluginManager().callEvent(
                new PlayerHarvestCropEvent(crop, block, player)
        );
    }


    /**
     * If the player can harvest the crop, then harvest it and replant it.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerHarvestCrop(@NotNull PlayerHarvestCropEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Crop crop = event.getCrop();

        if (!crop.canHarvest(player)) {
            event.setCancelled(true);
            return;
        }

        System.out.println("Player -- Harvest");

        if (crop instanceof TallCrop) {
            harvestAll(player, block, crop);
        } else {
            crop.harvest(player);
        }

        crop.replant(block);
        crop.playSounds(block);
        crop.playParticles(block);

        addonManager.applyEffects(player, crop);

        harvestedCrops.remove(crop);
    }


    /**
     * Harvest all the crops that is connected to the tall crop.
     *
     * @param player       The player who is harvesting the crop.
     * @param clickedBlock The block that was clicked.
     * @param crop         The crop that was clicked on
     */
    private void harvestAll(@NotNull Player player, @NotNull Block clickedBlock, @NotNull Crop crop) {
        int height = crop.getCurrentAge(clickedBlock);
        int actualHeight = getActualHeight(crop, height);
        for (int i = actualHeight; i > 0; --i) {
            crop.harvest(player);
        }
    }


    /**
     * If the crop should be replanted, return the height minus one, otherwise return the height.
     *
     * @param crop   The crop to be planted
     * @param height The height of the crop.
     *
     * @return The height of the crop.
     */
    private int getActualHeight(@NotNull Crop crop, int height) {
        return crop.shouldReplant() ? height - 1 : height;
    }

}