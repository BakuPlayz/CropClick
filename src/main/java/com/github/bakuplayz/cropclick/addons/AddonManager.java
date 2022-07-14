package com.github.bakuplayz.cropclick.addons;

import com.github.bakuplayz.cropclick.addons.addon.*;
import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class AddonManager {

    private final AddonsConfig addonsConfig;

    private @Getter mcMMOAddon mcMMOAddon;
    private @Getter TownyAddon townyAddon;
    private @Getter ResidenceAddon residenceAddon;
    private @Getter JobsRebornAddon jobsRebornAddon;
    private @Getter WorldGuardAddon worldGuardAddon;
    private @Getter OfflineGrowthAddon offlineGrowthAddon;


    public AddonManager(@NotNull AddonsConfig config) {
        this.addonsConfig = config;

        registerAddons();
    }

    private void registerAddons() {
        if (addonsConfig.isInstalled("mcMMO")) {
            this.mcMMOAddon = new mcMMOAddon(addonsConfig);
        }

        if (addonsConfig.isInstalled("Towny")) {
            this.townyAddon = new TownyAddon(addonsConfig);
        }

        if (addonsConfig.isInstalled("Residence")) {
            this.residenceAddon = new ResidenceAddon(addonsConfig);
        }

        if (addonsConfig.isInstalled("JobsReborn")) {
            this.jobsRebornAddon = new JobsRebornAddon(addonsConfig);
        }

        if (addonsConfig.isInstalled("WorldGuard")) {
            this.worldGuardAddon = new WorldGuardAddon(addonsConfig);
        }

        if (addonsConfig.isInstalled("OfflineGrowth")) {
            this.offlineGrowthAddon = new OfflineGrowthAddon(addonsConfig);
        }
    }

    public boolean isPresent(Addon addon) {
        return addon != null && addon.isEnabled();
    }

    // TODO: Requires a better name (canUse/canAffect)
    public boolean canModify(@NotNull Player player) {
        if (isPresent(townyAddon)) {
            return townyAddon.canDestroyCrop(player);
        }

        if (isPresent(residenceAddon)) {
            return residenceAddon.regionOrPlayerHasFlag(player);
        }

        if (isPresent(worldGuardAddon)) {
            return worldGuardAddon.regionAllowsPlayer(player);
        }

        return true;
    }

    public void applyEffects(@NotNull Player player) {
        if (isPresent(jobsRebornAddon)) {
            jobsRebornAddon.updateStats(player);
        }

        if (isPresent(mcMMOAddon)) {
            mcMMOAddon.addExperience(player);
        }
    }

}
