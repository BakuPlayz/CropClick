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
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.world.WorldManager;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import dev.bakuplayz.spigotstore.task.impl.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.Log.Tag;
import static com.github.bakuplayz.cropclick.common.Languages.Menu.UNLINK_ACTION_SUCCESS;


/**
 * A listener handling all the unlinking {@link Autofarm} events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerUnlinkAutofarmListener implements Listener {

    private final CropClick plugin;

    private final TaskScheduler taskScheduler;

    private final WorldManager worldManager;

    private final AutofarmManager autofarmManager;


    public PlayerUnlinkAutofarmListener(@NotNull CropClick plugin) {
        this.taskScheduler = plugin.getStore().getTaskScheduler();
        this.autofarmManager = plugin.getAutofarmManager();
        this.worldManager = plugin.getWorldManager();
        this.plugin = plugin;
    }


    /**
     * Handles all the {@link Player player} interact at {@link Autofarm autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtFarm(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (Blocks.isAir(block)) {
            return;
        }

        CropPlayer player = CropPlayer.fromPlayer(event.getPlayer());
        worldManager.getFinder().findByPlayer(player).thenAccept(world -> autofarmManager.getFinder().findByBlock(block).thenAccept(autofarm -> taskScheduler.runTask(() -> {
            if (!worldManager.isAccessible(world)) {
                return;
            }

            if (!player.getAddonFeatures().canModifyRegion()) {
                return;
            }

            if (!autofarmManager.isUsable(autofarm)) {
                return;
            }

            if (!player.getPermissions().canUnlink(autofarm)) {
                return;
            }

            Bukkit.getPluginManager().callEvent(
                    new PlayerUnlinkAutofarmEvent(player, autofarm)
            );
        }, TaskContext.BUKKIT)));
    }


    /**
     * Handles all the {@link Player player} unlink an {@link Autofarm autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerUnlinkAutofarm(@NotNull PlayerUnlinkAutofarmEvent event) {
        UNLINK_ACTION_SUCCESS.send(plugin, event.getPlayer());

        Log.debug("{0}: Called the unlink event.", Tag.PLAYER, event.getPlayer().getOfflinePlayer().getName());

        Bukkit.getPluginManager().callEvent(
                new AutofarmUnlinkEvent(event.getAutofarm())
        );
    }

}