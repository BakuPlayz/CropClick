package com.github.bakuplayz.cropclick.listeners.harvest;

import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.events.harvest.HarvestCropEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class HarvestCropListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onHarvestCrop(@NotNull HarvestCropEvent event) {
        if (event.isCancelled()) return;

        BaseCrop crop = event.getCrop();

        if (!crop.hasDrop()) {
            event.setCancelled(true);
        }

        System.out.println("Crop -- Harvest");
    }

}