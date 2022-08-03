package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PlayerUnlinkAutofarmListener implements Listener {

    private final CropClick plugin;

    private final AutofarmDataStorage farmData;

    private final WorldManager worldManager;
    private final AddonManager addonManager;
    private final AutofarmManager autofarmManager;


    public PlayerUnlinkAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.farmData = plugin.getFarmData();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.plugin = plugin;
    }


    /**
     * If the player is breaking a block, and the block is an autofarm, and the player has permission to unlink autofarms,
     * and the player is in a world where autofarms are allowed, and the player is allowed to modify autofarms, then unlink
     * the autofarm.
     *
     * @param event The event that is being listened to.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithFarm(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
        if (!PermissionUtils.canUnlink(player)) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (!autofarmManager.isUsable(autofarm)) {
            return;
        }

        autofarmManager.unlinkAutofarm(player, autofarm);
    }


    /**
     * When a player unlinks an autofarm, remove the metadata from the blocks and remove the autofarm from the farmData.
     *
     * @param event The event that is being listened for.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onUnlinkAutofarm(@NotNull PlayerUnlinkAutofarmEvent event) {
        if (event.isCancelled()) return;

        Autofarm autofarm = event.getAutofarm();
        Block crop = autofarm.getCropLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block dispenser = autofarm.getDispenserLocation().getBlock();

        DoublyLocation doublyContainer = LocationUtils.getAsDoubly(container);
        if (doublyContainer != null) {
            Location one = doublyContainer.getSingly();
            Location two = doublyContainer.getSingly();

            one.getBlock().removeMetadata("farmerID", plugin);
            two.getBlock().removeMetadata("farmerID", plugin);
        } else {
            container.removeMetadata("farmerID", plugin);
        }

        dispenser.removeMetadata("farmerID", plugin);
        crop.removeMetadata("farmerID", plugin);

        farmData.removeFarm(autofarm);
    }

}