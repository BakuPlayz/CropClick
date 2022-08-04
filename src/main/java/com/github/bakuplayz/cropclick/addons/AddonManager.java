package com.github.bakuplayz.cropclick.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.*;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AddonManager {

    private final CropClick plugin;

    private final AddonsConfig addonsConfig;

    private @Getter McMMOAddon mcMMOAddon;
    private @Getter TownyAddon townyAddon;
    private @Getter ResidenceAddon residenceAddon;
    private @Getter JobsRebornAddon jobsRebornAddon;
    private @Getter WorldGuardAddon worldGuardAddon;
    private @Getter OfflineGrowthAddon offlineGrowthAddon;

    private final List<Addon> registeredAddons;


    public AddonManager(@NotNull CropClick plugin) {
        this.registeredAddons = new ArrayList<>();
        this.addonsConfig = plugin.getAddonsConfig();
        this.plugin = plugin;

        registerAddons();
    }


    /**
     * If the config file says the addon is installed, then create a new instance of the addon class.
     */
    private void registerAddons() {
        if (addonsConfig.isInstalled("mcMMO")) {
            this.mcMMOAddon = new McMMOAddon(plugin);
            registeredAddons.add(mcMMOAddon);
        }

        if (addonsConfig.isInstalled("Towny")) {
            this.townyAddon = new TownyAddon(plugin);
            registeredAddons.add(townyAddon);
        }

        if (addonsConfig.isInstalled("Residence")) {
            this.residenceAddon = new ResidenceAddon(plugin);
            registeredAddons.add(residenceAddon);
        }

        if (addonsConfig.isInstalled("JobsReborn")) {
            this.jobsRebornAddon = new JobsRebornAddon(plugin);
            registeredAddons.add(jobsRebornAddon);
        }

        if (addonsConfig.isInstalled("WorldGuard")) {
            this.worldGuardAddon = new WorldGuardAddon(plugin);
            registeredAddons.add(worldGuardAddon);
        }

        if (addonsConfig.isInstalled("OfflineGrowth")) {
            this.offlineGrowthAddon = new OfflineGrowthAddon(plugin);
            registeredAddons.add(offlineGrowthAddon);
        }
    }


    /**
     * Toggle the addon with the given name.
     *
     * @param name The name of the addon.
     */
    public void toggle(@NotNull String name) {
        addonsConfig.toggle(name);
    }


    /**
     * Return the first addon in the list of registered addons that has the same name as the given name, or null if no such
     * addon exists.
     *
     * @param name The name of the addon you want to find.
     *
     * @return The first Addon that matches the name.
     */
    public Addon findByName(@NotNull String name) {
        return registeredAddons.stream()
                               .filter(addon -> addon.getName().equals(name))
                               .findFirst().orElse(null);
    }


    /**
     * If the addon is not null (aka. present) and is enabled, return true.
     *
     * @param addon The addon to check.
     *
     * @return A boolean value.
     */
    public boolean isPresentAndEnabled(Addon addon) {
        return addon != null && addon.isEnabled();
    }


    /**
     * Returns true if the given name is present in the list of registered addons.
     *
     * @param name The name of the addon to check for.
     *
     * @return A boolean value.
     */
    public boolean isPresent(@NotNull String name) {
        return registeredAddons.stream().anyMatch(addon -> addon.getName().equals(name));
    }


    /**
     * Return true if any enabled addon has the given name.
     *
     * @param name The name of the addon to check.
     *
     * @return A boolean value.
     */
    public boolean isEnabled(@NotNull String name) {
        return addonsConfig.isEnabled(name);
    }


    /**
     * If the player is in a Towny region, check if they can destroy crops. If they're in a Residence region, check if they
     * can destroy crops. If they're in a WorldGuard region, check if they can destroy crops. If they're not in any region,
     * return true.
     *
     * @param player The player who is trying to modify the block.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canModify(@NotNull Player player) {
        if (isPresentAndEnabled(townyAddon)) {
            return townyAddon.canDestroyCrop(player);
        }

        if (isPresentAndEnabled(residenceAddon)) {
            return residenceAddon.regionOrPlayerHasFlag(player);
        }

        if (isPresentAndEnabled(worldGuardAddon)) {
            return worldGuardAddon.regionAllowsPlayer(player);
        }

        return true;
    }


    /**
     * If the JobsReborn addon is present and enabled, update the player's stats. If the mcMMO addon is present and
     * enabled, add experience to the player
     *
     * @param player The player who harvested the crop.
     * @param crop   The crop that was harvested.
     */
    public void applyEffects(@NotNull Player player, @NotNull Crop crop) {
        if (isPresentAndEnabled(jobsRebornAddon)) {
            jobsRebornAddon.updateStats(player, crop);
        }

        if (isPresentAndEnabled(mcMMOAddon)) {
            mcMMOAddon.addExperience(player, crop);
        }
    }

}