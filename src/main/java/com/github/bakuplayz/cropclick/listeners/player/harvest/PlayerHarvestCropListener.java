/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.listeners.player.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.RoofCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.crop.crops.ground.SeaPickle;
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
import java.util.logging.Logger;


/**
 * A listener handling all the {@link Crop crop} harvest events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerHarvestCropListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final CropManager cropManager;
    private final AddonManager addonManager;
    private final WorldManager worldManager;
    private final PlayersConfig playersConfig;

    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    private final HashMap<Crop, Long> harvestedCrops;


    public PlayerHarvestCropListener(@NotNull CropClick plugin) {
        this.logger = plugin.getLogger();
        this.isDebugging = plugin.isDebugging();
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.playersConfig = plugin.getPlayersConfig();
        this.harvestedCrops = cropManager.getHarvestedCrops();
    }


    /**
     * Handles all the {@link Player player} interact at {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtCrop(@NotNull PlayerInteractEvent event) {
        if (!EventUtils.isMainHand(event.getHand())) {
            return;
        }

        Block block = event.getClickedBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Action action = event.getAction();
        if (EventUtils.isLeftClick(action)) {
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

        if (!addonManager.canModifyRegion(player)) {
            return;
        }

        Crop crop = cropManager.findByBlock(block);
        if (crop == null) {
            return;
        }

        if (harvestedCrops.containsKey(crop)) {
            return;
        }

        if (cropManager.isAlreadyClickable(crop)) {
            event.setCancelled(true);
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
     * Handles all the {@link Player player} harvest {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerHarvestCrop(@NotNull PlayerHarvestCropEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Crop crop = event.getCrop();

        harvestedCrops.remove(crop);

        if (!crop.canHarvest(player)) {
            event.setCancelled(true);
            return;
        }

        if (isDebugging) {
            logger.info(String.format("%s (Player): Called the harvest event!", player.getName()));
        }

        boolean wasHarvested;

        if (crop instanceof TallCrop) {
            TallCrop tallCrop = (TallCrop) crop;
            wasHarvested = tallCrop.harvestAll(player, block, crop);

        } else if (crop instanceof RoofCrop) {
            RoofCrop roofCrop = (RoofCrop) crop;
            wasHarvested = roofCrop.harvestAll(player, block, crop);

        } else if (crop instanceof SeaPickle) {
            SeaPickle seaPickle = (SeaPickle) crop;
            wasHarvested = seaPickle.harvestAll(player, block, crop);

        } else {
            wasHarvested = crop.harvest(player);
        }

        if (!wasHarvested) {
            event.setCancelled(true);
            return;
        }

        crop.replant(block);
        crop.playSounds(block);
        crop.playParticles(block);

        addonManager.applyEffects(player, crop);
    }


}