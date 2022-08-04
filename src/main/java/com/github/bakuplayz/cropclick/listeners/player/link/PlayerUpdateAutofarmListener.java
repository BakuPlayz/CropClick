package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.Event;
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
import org.bukkit.event.block.BlockBreakEvent;
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

    private final CropClick plugin;

    private final AutofarmManager autofarmManager;


    public PlayerUpdateAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceBlock(@NotNull BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!BlockUtils.isDoubleChest(block)) {
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


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerBreakBlock(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

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

        Autofarm oldAutofarm = event.getOldAutofarm();
        Autofarm newAutofarm = event.getNewAutofarm();

        autofarmManager.unlinkAutofarm(player, oldAutofarm);
        autofarmManager.linkAutofarm(player, newAutofarm);
    }

}