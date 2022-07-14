package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import es.yellowzaki.offlinegrowth.api.OfflineGrowthAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class OfflineGrowthAddon extends Addon {

    public OfflineGrowthAddon(@NotNull AddonsConfig config) {
        super("OfflineGrowth", config);
    }

    public void addCrop(@NotNull Location location) {
        OfflineGrowthAPI.addPlant(location);
    }

    public void removeCrop(@NotNull Location location) {
        OfflineGrowthAPI.removePlant(location);
    }

}
