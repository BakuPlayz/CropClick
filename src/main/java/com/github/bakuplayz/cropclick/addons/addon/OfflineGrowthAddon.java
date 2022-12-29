package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import es.yellowzaki.offlinegrowth.api.OfflineGrowthAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the OfflineGrowth addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
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