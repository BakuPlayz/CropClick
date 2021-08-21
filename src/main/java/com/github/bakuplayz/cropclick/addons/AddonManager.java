package com.github.bakuplayz.cropclick.addons;

import com.github.bakuplayz.cropclick.addons.addon.*;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public final class AddonManager {

    private final AddonsConfig addonsConfig;

    private @Getter mcMMOAddon mcMMOAddon;
    private @Getter TownyAddon townyAddon;
    private @Getter ResidenceAddon residenceAddon;
    private @Getter JobsRebornAddon jobsRebornAddon;
    private @Getter WorldGuardAddon worldGuardAddon;
    private @Getter OfflineGrowthAddon offlineGrowthAddon;

    public AddonManager(final @NotNull AddonsConfig addonsConfig) {
        this.addonsConfig = addonsConfig;

        registerAddons();
    }

    private void registerAddons() {
        if (addonsConfig.isEnabled("mcMMO")) {
            this.mcMMOAddon = new mcMMOAddon(addonsConfig);
        }

        if (addonsConfig.isEnabled("Towny")) {
            this.townyAddon = new TownyAddon(addonsConfig);
        }

        if (addonsConfig.isEnabled("Residence")) {
            this.residenceAddon = new ResidenceAddon(addonsConfig);
        }

        if (addonsConfig.isEnabled("JobsReborn")) {
            this.jobsRebornAddon = new JobsRebornAddon(addonsConfig);
        }

        if (addonsConfig.isEnabled("WorldGuard")) {
            this.worldGuardAddon = new WorldGuardAddon(addonsConfig);
        }

        if (addonsConfig.isEnabled("OfflineGrowth")) {
            this.offlineGrowthAddon = new OfflineGrowthAddon(addonsConfig);
        }
    }
}
