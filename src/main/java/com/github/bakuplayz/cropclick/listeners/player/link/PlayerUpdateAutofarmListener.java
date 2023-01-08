package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.Event;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUpdateEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUpdateAutofarmEvent;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.menu.menus.links.Component;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A listener handling all the update {@link Autofarm} events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerUpdateAutofarmListener implements Listener {

    private final CropClick plugin;

    private final AutofarmManager autofarmManager;


    public PlayerUpdateAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }


    /**
     * Handles all the {@link Player player} place a {@link Chest chest} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceDoubleChest(@NotNull BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            return;
        }

        Runnable chestRunnable = getDoubleChestRunnable(
                event.getPlayer(),
                event.getBlock()
        );

        Bukkit.getScheduler().runTaskLater(
                plugin,
                chestRunnable,
                1
        );
    }


    /**
     * Handles all the {@link Player player} update {@link Autofarm autofarm} link events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerUpdateAutofarm(@NotNull PlayerUpdateAutofarmEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        Autofarm oldFarm = event.getOldAutofarm();
        Autofarm newFarm = event.getNewAutofarm();
        if (!PermissionUtils.canUpdateOthersFarm(player, oldFarm.getOwnerID())) {
            event.setCancelled(true);
            return;
        }

        Event updateEvent = new AutofarmUpdateEvent(
                oldFarm,
                newFarm
        );

        if (plugin.isDebugging()) {
            plugin.getLogger().info(String.format("%s (Player): Called the update event!", player.getName()));
        }

        Bukkit.getPluginManager().callEvent(updateEvent);
    }


    /**
     * Gets the runnable for checking updating an {@link Autofarm autofarm's} container {@link Component} based on the created {@link DoubleChest double chest}.
     *
     * @param player the player updating the autofarm.
     * @param block  the block where the chest was placed.
     *
     * @return a runnable for updating the chest component.
     */
    @Contract(pure = true)
    private @NotNull Runnable getDoubleChestRunnable(@NotNull Player player, @NotNull Block block) {
        return () -> {
            if (!BlockUtils.isDoubleChest(block)) {
                return;
            }

            Autofarm autofarm = autofarmManager.findAutofarm(block);
            if (autofarm == null) {
                return;
            }

            DoublyLocation doubleChest = LocationUtils.findDoubly(block.getLocation());

            if (doubleChest == null) {
                return;
            }

            Autofarm newAutofarm = new Autofarm(
                    autofarm.getFarmerID(),
                    autofarm.getOwnerID(),
                    autofarm.isEnabled(),
                    autofarm.getCropLocation(),
                    doubleChest,
                    autofarm.getDispenserLocation()
            );

            Event updateEvent = new PlayerUpdateAutofarmEvent(
                    player,
                    autofarm,
                    newAutofarm
            );

            Bukkit.getPluginManager().callEvent(updateEvent);
        };
    }

}