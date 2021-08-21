package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.addons.addon.template.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import es.yellowzaki.offlinegrowth.api.OfflineGrowthAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;


public final class OfflineGrowthAddon extends Addon {

    public OfflineGrowthAddon(final @NotNull AddonsConfig addonsConfig) {
        super("OfflineGrowth", addonsConfig);
    }

    public void addCrop(final @NotNull Location location) {
        OfflineGrowthAPI.addPlant(location);
    }

    public void removeCrop(final @NotNull Location location) {
        OfflineGrowthAPI.removePlant(location);
    }
}
