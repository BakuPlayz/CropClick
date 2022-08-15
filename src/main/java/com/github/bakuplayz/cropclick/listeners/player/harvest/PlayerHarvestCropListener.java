package com.github.bakuplayz.cropclick.listeners.player.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.SeaPickle;
import com.github.bakuplayz.cropclick.crop.crops.SweetBerries;
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
    private final PlayersConfig playersConfig;

    private final HashMap<Crop, Long> harvestedCrops;


    public PlayerHarvestCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.playersConfig = plugin.getPlayersConfig();
        this.harvestedCrops = cropManager.getHarvestedCrops();
    }


    /**
     * If the player is allowed to harvest the crop, then call the PlayerHarvestCropEvent.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithCrop(@NotNull PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Action action = event.getAction();
        if (!EventUtils.isRightClick(action)) {
            return;
        }

        Player player = event.getPlayer();
        if (!playersConfig.isEnabled(player)) {
            return;
        }

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

        if (crop instanceof SweetBerries) {
            event.setCancelled(true);
        }

        if (harvestedCrops.containsKey(crop)) {
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
            TallCrop tallCrop = (TallCrop) crop;
            tallCrop.harvestAll(player, block, crop);
        } else if (crop instanceof SeaPickle) {
            SeaPickle seaPickle = (SeaPickle) crop;
            seaPickle.harvestAll(player, block, crop);
        } else {
            crop.harvest(player);
        }

        crop.replant(block);
        crop.playSounds(block);
        crop.playParticles(block);

        addonManager.applyEffects(player, crop);

        harvestedCrops.remove(crop);
    }


}