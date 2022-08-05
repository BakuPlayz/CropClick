package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.Event;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUpdateEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUpdateAutofarmEvent;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PlayerUpdateAutofarmListener implements Listener {

    private final AutofarmManager autofarmManager;


    public PlayerUpdateAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
    }


    /**
     * If a player places a block, and that block is a double chest, and that double chest is part of an autofarm, then
     * update the autofarm to use the double chest.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceDoubleChest(@NotNull BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!BlockUtils.isDoubleChest(block)) {
            return;
        }

        if (!autofarmManager.isEnabled()) {
            return;
        }

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (autofarm == null) {
            return;
        }

        DoublyLocation doubleChest = BlockUtils.getAsDoubleChest(block);

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
                event.getPlayer(),
                autofarm,
                newAutofarm
        );

        Bukkit.getPluginManager().callEvent(updateEvent);
    }


    /**
     * If the player has permission to update their autofarm, then unlink the old autofarm and link the new autofarm.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerUpdateAutofarm(@NotNull PlayerUpdateAutofarmEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        if (!PermissionUtils.canUpdateFarm(player)) {
            event.setCancelled(true);
            return;
        }

        Event updateEvent = new AutofarmUpdateEvent(
                event.getOldAutofarm(),
                event.getNewAutofarm()
        );

        System.out.println("Player -- Update");

        Bukkit.getPluginManager().callEvent(updateEvent);
    }

}