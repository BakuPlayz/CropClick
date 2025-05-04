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
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.common.Events;
import com.github.bakuplayz.cropclick.common.PermissionUtils;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.crops.MassHarvestable;
import com.github.bakuplayz.cropclick.events.player.harvest.PlayerHarvestCropEvent;
import com.github.bakuplayz.cropclick.mappers.ComponentMapper;
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
 * A listener handling all the {@link Crop crop} harvest events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerHarvestCropListener implements Listener {

    private final CropManager cropManager;

    private final WorldManager worldManager;


    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    private final HashMap<Crop, Long> harvestedCrops;


    public PlayerHarvestCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.harvestedCrops = cropManager.getHarvestedCrops();
    }


    /**
     * Handles all the {@link Player player} interact at {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtCrop(@NotNull PlayerInteractEvent event) {
        if (Versions.hasMainHand() && !Events.isMainHand(event.getHand())) {
            return;
        }

        Block block = event.getClickedBlock();
        if (Blocks.isAir(block)) {
            return;
        }

        Action action = event.getAction();
        if (Events.isLeftClick(action)) {
            return;
        }

        CropPlayer player = CropPlayer.of(event.getPlayer());
        if (!player.isPluginEnabled()) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player.getBukkitPlayer().getPlayer());
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!world.allowsPlayers()) {
            return;
        }

        if (!player.getAddonFunctionality().canModifyRegion()) {
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

        CropPlayer player = event.getPlayer();
        Block block = event.getBlock();
        Crop crop = event.getCrop();

        harvestedCrops.remove(crop);

        if (!crop.canHarvest(player)) {
            event.setCancelled(true);
            return;
        }

        Log.debug("{} (Player): Called the harvest event!", player.getBukkitPlayer().getName());

        ContainerComponent container = ComponentMapper.getContainer().of(
                player.getBukkitPlayer().getPlayer()
        );

        if (!tryHarvest(crop, container, block)) {
            event.setCancelled(true);
            return;
        }

        crop.replant(block);
        crop.playSounds(block);
        crop.playParticles(block);

        player.getAddonFunctionality().updateStats(crop);
    }


    private boolean tryHarvest(@NotNull Crop crop, @NotNull ContainerComponent container, @NotNull Block block) {
        if (crop instanceof MassHarvestable) {
            return ((MassHarvestable) crop).harvestAll(container, block);
        }
        return crop.harvest(container);
    }

}