package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.utils.VersionUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Crops;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class PlayerUnlinkAutofarmListener implements Listener {

    private final CropClick plugin;
    private final AutofarmManager manager;
    private final AutofarmDataStorage dataStorage;

    //rework the following

    public PlayerUnlinkAutofarmListener(final @NotNull CropClick plugin) {
        this.dataStorage = plugin.getAutofarmDataStorage();
        this.manager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerUnlinksAutofarm(final @NotNull BlockBreakEvent event) {
        Block block = event.getBlock();
        BlockState state = block.getState();
        Player player = event.getPlayer();

        if (!player.hasPermission("cropclick.autofarmer.link")) {
            return;
        }

        //if state instanceof CustomCrop

        if (VersionUtil.supportsShulkers()) {
            if (!(state instanceof Dispenser) &&
                    !(state instanceof Crops) &&
                    !(state instanceof Chest) &&
                    !(state instanceof ShulkerBox) &&
                    !hasCropOnTop(block)) {
                return;
            }
        } else {
            if (!(state instanceof Dispenser) &&
                    !(state instanceof Crops) &&
                    !(state instanceof Chest) &&
                    !hasCropOnTop(block)) {
                return;
            }
        }

        Autofarm autofarm = manager.findAutofarm(block);
        if (autofarm == null) return;

        manager.unlinkAutofarm(event.getPlayer(), autofarm);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onUnlinkAutofarm(final @NotNull PlayerUnlinkAutofarmEvent event) {
        Autofarm autofarm = event.getAutofarm();

        if (!manager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        Block crop = autofarm.getCropLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block dispenser = autofarm.getDispenserLocation().getBlock();

        crop.removeMetadata("farmerID", plugin);
        container.removeMetadata("farmerID", plugin);
        dispenser.removeMetadata("autofarm", plugin);

        dataStorage.removeAutofarm(autofarm);
    }

    public boolean hasCropOnTop(final @NotNull Block block) {
        if (block.getType() != Material.SOIL) return false; // Won't work for cocoa beans

        Location topLocation = block.getLocation();
        topLocation.setY(topLocation.getBlockY() + 1); // Won't work for cocoa beans
        return topLocation.getBlock().getState() instanceof Crops;
    }
}
