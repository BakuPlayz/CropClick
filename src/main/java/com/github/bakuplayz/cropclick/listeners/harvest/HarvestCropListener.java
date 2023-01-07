package com.github.bakuplayz.cropclick.listeners.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.harvest.HarvestCropEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * A listener handling all the {@link Crop crop} harvest events.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class HarvestCropListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;


    public HarvestCropListener(@NotNull CropClick plugin) {
        this.isDebugging = plugin.isDebugging();
        this.logger = plugin.getLogger();
    }


    /**
     * Handles all the harvest {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onHarvestCrop(@NotNull HarvestCropEvent event) {
        if (event.isCancelled()) return;

        Crop crop = event.getCrop();

        if (!crop.hasDrop()) {
            event.setCancelled(true);
        }

        if (isDebugging) {
            logger.info(String.format("%s (Crop): Called the harvest event!", crop.getName()));
        }
    }

}