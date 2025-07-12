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

package com.github.bakuplayz.cropclick.listeners.player.destory;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.offlinegrowth.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.events.player.destroy.PlayerDestroyCropEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.permissions.PermissionKey;
import com.github.bakuplayz.cropclick.world.WorldManager;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import dev.bakuplayz.spigotstore.task.impl.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.Log.Tag;


/**
 * A listener handling all the {@link Crop crop} destroy events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerDestroyCropListener implements Listener {

    private final CropManager cropManager;

    private final WorldManager worldManager;

    private final AddonManager addonManager;

    private final TaskScheduler taskScheduler;

    private final AutofarmManager autofarmManager;

    private final OfflineGrowthAddon growthAddon;


    public PlayerDestroyCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.taskScheduler = plugin.getTaskScheduler();
        this.autofarmManager = plugin.getAutofarmManager();
        this.growthAddon = addonManager.getOfflineGrowthAddon();
    }


    /**
     * Handles all the {@link Player player} interact at {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtCrop(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (Blocks.isAir(block)) {
            return;
        }

        CropPlayer player = CropPlayer.fromPlayer(event.getPlayer());
        worldManager.getFinder().findByPlayer(player).thenAccept(world -> taskScheduler.runTask(() -> {
            if (!worldManager.isAccessible(world)) {
                return;
            }

            if (!world.allowsPlayers()) {
                return;
            }

            Crop crop = cropManager.getFinder().findByBlock(block);
            Crop cropAbove = cropManager.getFinder().findByBlock(block.getRelative(BlockFace.UP));
            if (crop == null) {
                if (cropAbove == null) {
                    return;
                }
                crop = cropAbove;
            }

            if (!player.getPermissions().has(PermissionKey.CROP_DESTROY, crop.getName())) {
                return;
            }

            if (!player.getAddonFeatures().canModifyRegion()) {
                event.setCancelled(true);
                return;
            }

            Bukkit.getPluginManager().callEvent(
                    new PlayerDestroyCropEvent(crop, block, player)
            );
        }, TaskContext.BUKKIT));
    }


    /**
     * Handles all the {@link Player player} destroy {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler
    public void onPlayerDestroyCrop(@NotNull PlayerDestroyCropEvent event) {
        Block block = event.getBlock();
        CropPlayer player = event.getPlayer();

        if (addonManager.isInstalledAndEnabled(growthAddon)) {
            growthAddon.getFunctionality().unregisterCrop(block.getLocation());
        }

        autofarmManager.getFinder().findByBlock(block).thenAccept(autofarm -> {
            if (!autofarmManager.isUsable(autofarm)) {
                event.setCancelled(true);
                return;
            }

            Log.debug("{0}: Called the destroy crop event.", Tag.PLAYER, player.getOfflinePlayer().getName());

            Bukkit.getPluginManager().callEvent(
                    new PlayerUnlinkAutofarmEvent(player, autofarm)
            );
        });
    }

}