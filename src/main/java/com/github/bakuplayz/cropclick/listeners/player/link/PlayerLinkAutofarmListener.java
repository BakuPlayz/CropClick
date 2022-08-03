package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.metadata.AutofarmMetadata;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PlayerLinkAutofarmListener implements Listener {

    private final CropClick plugin;
    private final AutofarmDataStorage farmData;


    public PlayerLinkAutofarmListener(@NotNull CropClick plugin) {
        this.farmData = plugin.getFarmData();
        this.plugin = plugin;
    }


    /**
     * It adds metadata to the blocks that make up the autofarm, and then adds the autofarm to the farm data.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmLink(@NotNull PlayerLinkAutofarmEvent event) {
        if (event.isCancelled()) return;

        Autofarm autofarm = event.getAutofarm();
        Block crop = autofarm.getCropLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block dispenser = autofarm.getDispenserLocation().getBlock();

        AutofarmMetadata farmerMeta = new AutofarmMetadata(plugin, autofarm::getFarmerID);

        DoublyLocation doublyLocation = LocationUtils.getAsDoubly(container);
        if (doublyLocation != null) {
            Block singly = doublyLocation.getSingly().getBlock();
            Block doubly = doublyLocation.getDoubly().getBlock();

            singly.setMetadata("farmerID", farmerMeta);
            doubly.setMetadata("farmerID", farmerMeta);

            autofarm.setContainerLocation(doublyLocation);
        } else {
            container.setMetadata("farmerID", farmerMeta);
        }

        dispenser.setMetadata("farmerID", farmerMeta);
        crop.setMetadata("farmerID", farmerMeta);

        farmData.addFarm(autofarm);
    }

}