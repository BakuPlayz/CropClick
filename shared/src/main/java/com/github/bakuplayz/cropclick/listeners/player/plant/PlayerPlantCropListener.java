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

package com.github.bakuplayz.cropclick.listeners.player.plant;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.offlinegrowth.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.common.Events;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.events.player.plant.PlayerPlantCropEvent;
import com.github.bakuplayz.cropclick.permissions.PermissionKey;
import com.github.bakuplayz.cropclick.world.WorldManager;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import dev.bakuplayz.spigotstore.task.impl.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.Log.Tag;

/**
 * A listener handling all the {@link Crop crop} plant events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerPlantCropListener implements Listener {

    private final CropManager cropManager;

    private final AddonManager addonManager;

    private final WorldManager worldManager;

    private final TaskScheduler taskScheduler;

    private final OfflineGrowthAddon growthAddon;


    public PlayerPlantCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.taskScheduler = plugin.getStore().getTaskScheduler();
        this.growthAddon = addonManager.getOfflineGrowthAddon();
    }


    /**
     * Handles all the {@link Player player} place {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceCrop(@NotNull BlockPlaceEvent event) {
        if (Versions.hasMainHand() && !Events.isMainHand(event.getHand())) {
            return;
        }

        Block block = event.getBlock();
        if (Blocks.isAir(block)) {
            return;
        }

        CropPlayer player = CropPlayer.fromPlayer(event.getPlayer());
        worldManager.getFinder().findByPlayer(player).thenAccept(world -> taskScheduler.runTask(() -> {
            if (!worldManager.isAccessible(world)) {
                return;
            }

            if (!player.getAddonFeatures().canModifyRegion()) {
                return;
            }

            Crop crop = cropManager.getFinder().findByBlock(block);
            if (crop == null) {
                return;
            }

            if (!player.getPermissions().has(PermissionKey.CROP_PLANT, crop.getName())) {
                return;
            }

            Bukkit.getPluginManager().callEvent(
                    new PlayerPlantCropEvent(crop, block, player)
            );
        }, TaskContext.BUKKIT));
    }


    /**
     * Handles all the {@link Player player} plant {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlantCrop(@NotNull PlayerPlantCropEvent event) {
        Log.debug("{0}: Called the plant crop event.", Tag.PLAYER, event.getPlayer().getOfflinePlayer().getName());

        if (addonManager.isInstalledAndEnabled(growthAddon)) {
            growthAddon.getFunctionality().registerCrop(event.getBlock().getLocation());
        }
    }

}