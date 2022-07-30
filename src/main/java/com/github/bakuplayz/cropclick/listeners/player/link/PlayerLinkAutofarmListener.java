package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.metadata.AutofarmMetadata;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.utils.LocationUtil;
import org.bukkit.Location;
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
        Autofarm autofarm = event.getAutofarm();

        Block crop = autofarm.getCropLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block dispenser = autofarm.getDispenserLocation().getBlock();

        AutofarmMetadata farmerMeta = new AutofarmMetadata(plugin, autofarm::getFarmerID);

        DoublyLocation doublyContainer = LocationUtil.findByBlock(container);
        if (doublyContainer != null) {
            Location one = doublyContainer.getSingly();
            Location two = doublyContainer.getSingly();

            one.getBlock().setMetadata("farmerID", farmerMeta);
            two.getBlock().setMetadata("farmerID", farmerMeta);
        } else {
            container.setMetadata("farmerID", farmerMeta);
        }

        dispenser.setMetadata("farmerID", farmerMeta);
        crop.setMetadata("farmerID", farmerMeta);

        farmData.addFarm(autofarm);
    }

}