package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import es.yellowzaki.offlinegrowth.api.OfflineGrowthAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class OfflineGrowthAddon extends Addon {

    public OfflineGrowthAddon(@NotNull CropClick plugin) {
        super(plugin, "OfflineGrowth");
    }


    /**
     * Adds a plant to the OfflineGrowth database/config.
     *
     * @param location The location of the crop.
     */
    public void addCrop(@NotNull Location location) {
        OfflineGrowthAPI.addPlant(location);
    }


    /**
     * Removes a plant from the OfflineGrowth database/config.
     *
     * @param location The location of the crop
     */
    public void removeCrop(@NotNull Location location) {
        OfflineGrowthAPI.removePlant(location);
    }

}