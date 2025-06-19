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

package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.common.Autofarms;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.common.Events;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtContainerEvent;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtCropEvent;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtDispenserEvent;
import com.github.bakuplayz.cropclick.world.WorldManager;
import dev.bakuplayz.spigotstore.task.TaskContext;
import dev.bakuplayz.spigotstore.task.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.DefaultConfig.ConfigurationKey;


/**
 * A listener handling all the {@link Autofarm} interactions caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerInteractAtAutofarmListener implements Listener {

    private final DefaultConfig config;

    private final CropManager cropManager;

    private final WorldManager worldManager;

    private final AutofarmManager autofarmManager;

    private final TaskScheduler taskScheduler;


    public PlayerInteractAtAutofarmListener(@NotNull CropClick plugin) {
        this.config = plugin.getConfigManager().getDefaultConfig();
        this.autofarmManager = plugin.getAutofarmManager();
        this.taskScheduler = plugin.getTaskScheduler();
        this.worldManager = plugin.getWorldManager();
        this.cropManager = plugin.getCropManager();
    }


    /**
     * Handles all the {@link Player player} interact at {@link Block block} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtBlock(@NotNull PlayerInteractEvent event) {
        if (Versions.hasMainHand() && !Events.isMainHand(event.getHand())) {
            return;
        }

        Block block = event.getClickedBlock();
        if (Blocks.isAir(block)) {
            return;
        }

        Action action = event.getAction();
        CropPlayer player = CropPlayer.fromPlayer(event.getPlayer());
        if (!Events.isLeftShift(player, action)) {
            return;
        }

        if (!player.isPluginEnabled()) {
            return;
        }

        if (!config.getBoolean(ConfigurationKey.AUTOFARMS_ENABLED)) {
            return;
        }

        if (!player.getAddonFeatures().canModifyRegion()) {
            return;
        }

        autofarmManager.getFinder().findByBlock(block).thenAccept(autofarm -> worldManager.getFinder().findByPlayer(player).thenAccept(world -> taskScheduler.runTask(() -> {
            if (!player.getPermissions().canInteractAt(autofarm)) {
                return;
            }

            if (!worldManager.isAccessible(world)) {
                return;
            }

            if (!world.allowsPlayers()) {
                return;
            }

            if (Autofarms.isContainer(block)) {
                event.setCancelled(true);

                Bukkit.getPluginManager().callEvent(
                        new PlayerInteractAtContainerEvent(block, player, Autofarms.findContainer(block), autofarm)
                );
            }

            if (Autofarms.isDispenser(block)) {
                event.setCancelled(true);

                Bukkit.getPluginManager().callEvent(
                        new PlayerInteractAtDispenserEvent(player, Autofarms.findDispenser(block), autofarm)
                );
            }

            if (Autofarms.isCrop(cropManager, block)) {
                event.setCancelled(true);

                Bukkit.getPluginManager().callEvent(
                        new PlayerInteractAtCropEvent(Autofarms.findCrop(cropManager, block), block, player, autofarm)
                );
            }
        }, TaskContext.BUKKIT)));
    }

}