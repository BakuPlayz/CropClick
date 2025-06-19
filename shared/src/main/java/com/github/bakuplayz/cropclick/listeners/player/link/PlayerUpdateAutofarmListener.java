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

package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.common.Locations;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUpdateEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUpdateAutofarmEvent;
import dev.bakuplayz.spigotstore.task.TaskContext;
import dev.bakuplayz.spigotstore.task.TaskScheduler;
import dev.bakuplayz.spigotstore.task.model.Task;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.Log.Tag;


/**
 * A listener handling all the update {@link Autofarm} events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerUpdateAutofarmListener implements Listener {

    private final DefaultConfig config;

    private final TaskScheduler taskScheduler;

    private final AutofarmManager autofarmManager;


    public PlayerUpdateAutofarmListener(@NotNull CropClick plugin) {
        this.config = plugin.getConfigManager().getDefaultConfig();
        this.autofarmManager = plugin.getAutofarmManager();
        this.taskScheduler = plugin.getTaskScheduler();
    }


    /**
     * Handles all the {@link Player player} place a {@link Chest chest} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceDoubleChest(@NotNull BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        if (!config.getBoolean(DefaultConfig.ConfigurationKey.AUTOFARMS_ENABLED)) {
            return;
        }

        Task chestRunnable = getDoubleChestTask(
                CropPlayer.fromPlayer(event.getPlayer()),
                event.getBlock()
        );

        taskScheduler.runTask(chestRunnable, TaskContext.BUKKIT);
    }


    /**
     * Handles all the {@link Player player} update {@link Autofarm autofarm} link events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerUpdateAutofarm(@NotNull PlayerUpdateAutofarmEvent event) {
        CropPlayer player = event.getPlayer();
        Autofarm oldFarm = event.getOldAutofarm();
        Autofarm newFarm = event.getNewAutofarm();
        if (player.getPermissions().canUpdate(oldFarm)) {
            event.setCancelled(true);
            return;
        }

        Log.debug("{0}: Called the update event.", Tag.PLAYER, player.getOfflinePlayer().getName());

        Bukkit.getPluginManager().callEvent(
                new AutofarmUpdateEvent(oldFarm, newFarm)
        );
    }


    /**
     * Gets the runnable for checking updating an {@link Autofarm autofarm's} container component based on the created {@link DoubleChest double chest}.
     *
     * @param player the player updating the autofarm.
     * @param block  the block where the chest was placed.
     *
     * @return a runnable for updating the chest component.
     */
    @NotNull
    private Task getDoubleChestTask(@NotNull CropPlayer player, @NotNull Block block) {
        return () -> {
            DoublyLocation doubleChest = Locations.findDoubly(block.getLocation());
            if (doubleChest == null) {
                return;
            }

            autofarmManager.getFinder().findByBlock(block).thenAccept(autofarm -> {
                if (autofarm == null) {
                    return;
                }

                Autofarm newAutofarm = Autofarm.createBasic(
                        autofarm.getFarmerId(),
                        autofarm.getOwnerId(),
                        autofarm.isEnabled(),
                        autofarm.getCropLocation(),
                        doubleChest,
                        autofarm.getDispenserLocation()
                );

                Bukkit.getPluginManager().callEvent(
                        new PlayerUpdateAutofarmEvent(player, autofarm, newAutofarm)
                );
            });
        };
    }

}