package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import com.github.bakuplayz.cropclick.utils.PermissionUtil;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
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

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithFarm(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (BlockUtil.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
        if (!PermissionUtil.canUnlink(player)) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessable(world)) {
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

    @EventHandler(priority = EventPriority.LOW)
    public void onUnlinkAutofarm(@NotNull PlayerUnlinkAutofarmEvent event) {
        if (event.isCancelled()) return;

        Autofarm autofarm = event.getAutofarm();
        Block crop = autofarm.getCropLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block dispenser = autofarm.getDispenserLocation().getBlock();

        crop.removeMetadata("farmerID", plugin);
        container.removeMetadata("farmerID", plugin);
        dispenser.removeMetadata("autofarm", plugin);

        farmData.removeFarm(autofarm);
    }

}

