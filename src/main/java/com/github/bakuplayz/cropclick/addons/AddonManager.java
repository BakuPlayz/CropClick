/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.*;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * A class managing the {@link Addon addons}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
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

    /**
     * A variable containing all the {@link Addon registed addons}.
     */
    private final List<Addon> registeredAddons;


    public AddonManager(@NotNull CropClick plugin) {
        this.registeredAddons = new ArrayList<>();
        this.addonsConfig = plugin.getAddonsConfig();
        this.plugin = plugin;

        registerAddons();
    }


    /**
     * Registers the all the {@link Addon installed addons}.
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
     * Toggles the {@link Addon addon} based on the provided name.
     *
     * @param addonName the name of the addon.
     */
    public void toggleAddon(@NotNull String addonName) {
        addonsConfig.toggleAddon(addonName);
    }


    /**
     * Finds the {@link Addon addon} based on the provided name.
     *
     * @param name the name of the addon.
     *
     * @return the found addon, otherwise null.
     */
    public @Nullable Addon findByName(@NotNull String name) {
        return registeredAddons.stream()
                               .filter(addon -> addon.getName().equals(name))
                               .findFirst().orElse(null);
    }


    /**
     * Checks whether the {@link Addon provided addon} is installed and enabled.
     *
     * @param addon the addon to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isInstalledAndEnabled(Addon addon) {
        return addon != null && addon.isEnabled();
    }


    /**
     * Checks whether the {@link Addon addon} isInstalled based on the provided name.
     *
     * @param addonName the name of the addon.
     *
     * @return true if installed, otherwise false.
     */
    public boolean isInstalled(@NotNull String addonName) {
        return registeredAddons.stream().anyMatch(addon -> addon.getName().equals(addonName));
    }


    /**
     * Checks whether the {@link Addon addon} is enabled based on the provided name.
     *
     * @param addonName the name of the addon.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled(@NotNull String addonName) {
        return addonsConfig.isEnabled(addonName);
    }


    /**
     * Checks if the {@link Player provided player} is allowed to modify the current region.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canModifyRegion(@NotNull Player player) {
        if (isInstalledAndEnabled(townyAddon)) {
            return townyAddon.canDestroyCrop(player);
        }

        if (isInstalledAndEnabled(residenceAddon)) {
            return residenceAddon.isMemberOfRegion(player)
                    || residenceAddon.hasRegionFlag(player.getLocation());
        }

        if (isInstalledAndEnabled(worldGuardAddon)) {
            return worldGuardAddon.regionAllowsPlayer(player);
        }

        return true;
    }


    /**
     * Applies the effects of all the {@link Addon addons}.
     *
     * @param player the player to receive the effects.
     * @param crop   the crop to find the effects with.
     */
    public void applyEffects(@NotNull Player player, @NotNull Crop crop) {
        if (isInstalledAndEnabled(jobsRebornAddon)) {
            jobsRebornAddon.updateStats(player, crop);
        }

        if (isInstalledAndEnabled(mcMMOAddon)) {
            mcMMOAddon.addExperience(player, crop);
        }
    }

}