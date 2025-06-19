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
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.addons.auraskills.AuraSkillsAddon;
import com.github.bakuplayz.cropclick.addons.jobsreborn.JobsRebornAddon;
import com.github.bakuplayz.cropclick.addons.mcmmo.MCMMOAddon;
import com.github.bakuplayz.cropclick.addons.offlinegrowth.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.addons.residence.ResidenceAddon;
import com.github.bakuplayz.cropclick.addons.towny.TownyAddon;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * A class managing the {@link AbstractAddon addons}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
public final class AddonManager {

    /**
     * A variable containing all the {@link AbstractAddon registed addons}.
     */
    private final List<AbstractAddon> registeredAddons;

    private MCMMOAddon mcMMOAddon;

    private TownyAddon townyAddon;

    private AuraSkillsAddon auraSkillsAddon;

    private ResidenceAddon residenceAddon;

    private JobsRebornAddon jobsRebornAddon;

    private OfflineGrowthAddon offlineGrowthAddon;


    public AddonManager() {
        this.registeredAddons = new ArrayList<>();
    }


    /**
     * Registers the all the {@link AbstractAddon installed addons}.
     */
    public void registerAddons(@NotNull CropClick plugin) {
        this.mcMMOAddon = new MCMMOAddon(plugin);
        this.townyAddon = new TownyAddon(plugin);
        this.auraSkillsAddon = new AuraSkillsAddon(plugin);
        this.residenceAddon = new ResidenceAddon(plugin);
        this.jobsRebornAddon = new JobsRebornAddon(plugin);
        this.offlineGrowthAddon = new OfflineGrowthAddon(plugin);

        registerAddon(mcMMOAddon);
        registerAddon(townyAddon);
        registerAddon(auraSkillsAddon);
        registerAddon(residenceAddon);
        registerAddon(jobsRebornAddon);
        registerAddon(offlineGrowthAddon);
    }


    /**
     * Registers the provided {@link AbstractAddon addon}.
     *
     * @param addon the addon to register.
     */
    private void registerAddon(@NotNull AbstractAddon addon) {
        if (addon.isInstalled()) {
            addon.setup();
            registeredAddons.add(addon);
        }
    }


    /**
     * Finds the {@link AbstractAddon addon} based on the provided name.
     *
     * @param name the name of the addon.
     *
     * @return the found addon, otherwise null.
     */
    @Nullable
    public AbstractAddon findByName(@NotNull String name) {
        return registeredAddons.stream()
                       .filter(addon -> addon.getName().equals(name))
                       .findFirst().orElse(null);
    }


    /**
     * Checks whether the {@link AbstractAddon provided addon} is installed and enabled.
     *
     * @param addon the addon to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isInstalledAndEnabled(@NotNull AbstractAddon addon) {
        return addon.getFunctionality() != null && addon.isEnabled();
    }


    public int getAmountOfAddons() {
        return 6;
    }

}