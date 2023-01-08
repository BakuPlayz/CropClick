package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import es.yellowzaki.offlinegrowth.api.OfflineGrowthAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the <a href="https://www.spigotmc.org/resources/offlinegrowth-plants-grow-on-unloaded-chunks-1-8x-1-19x.87615/">OfflineGrowth</a> addon.
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
     * Registers the {@link Crop crop} at the {@link Location provided location}.
     *
     * @param location the location of the crop to register.
     */
    public void registerCrop(@NotNull Location location) {
        OfflineGrowthAPI.addPlant(location);
    }


    /**
     * Unregisters the {@link Crop crop} at the {@link Location provided location}.
     *
     * @param location the location of the crop to unregister.
     */
    public void unregisterCrop(@NotNull Location location) {
        OfflineGrowthAPI.removePlant(location);
    }

}