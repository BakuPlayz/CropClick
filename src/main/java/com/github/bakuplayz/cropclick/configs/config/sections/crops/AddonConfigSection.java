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

package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.addons.addon.AureliumAddon;
import com.github.bakuplayz.cropclick.addons.addon.JobsRebornAddon;
import com.github.bakuplayz.cropclick.addons.addon.McMMOAddon;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the addon configuration section.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AddonConfigSection extends ConfigSection {

    private final CropsConfig cropsConfig;


    public AddonConfigSection(@NotNull CropsConfig config) {
        super(config.getConfig());
        this.cropsConfig = config;
    }


    /**
     * Gets the {@link McMMOAddon mcMMO} experience for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the mcMMO experience for the crop.
     */
    public int getMcMMOExperience(@NotNull String cropName) {
        return config.getInt("crops." + cropName + ".addons.mcMMO.experience", 0);
    }


    /**
     * Sets the {@link McMMOAddon mcMMO} experience for the {@link Crop provided crop} to the provided amount.
     *
     * @param cropName   the name of the crop.
     * @param experience the experience to set.
     */
    public void setMcMMOExperience(@NotNull String cropName, double experience) {
        config.set("crops." + cropName + ".addons.mcMMO.experience", experience);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link McMMOAddon mcMMO} experience reason for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the mcMMO experience reason for the crop.
     */
    @Contract("_ -> new")
    public @NotNull String getMcMMOExperienceReason(@NotNull String cropName) {
        return MessageUtils.colorize(
                config.getString(
                        "crops." + cropName + ".addons.mcMMO.experienceReason",
                        "Harvested " + cropName
                )
        );
    }


    /**
     * Sets the {@link McMMOAddon mcMMO} experience reason for the {@link Crop provided crop} to the provided reason.
     *
     * @param cropName the name of the crop.
     * @param reason   the reason to set.
     */
    public void setMcMMOExperienceReason(@NotNull String cropName, @NotNull String reason) {
        config.set("crops." + cropName + ".addons.mcMMO.experienceReason", reason);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link JobsRebornAddon JobsReborn} points for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the JobsReborn points for the crop.
     */
    public double getJobsPoints(@NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".addons.jobsReborn.points", 0);
    }


    /**
     * Sets the {@link JobsRebornAddon JobsReborn} points for the {@link Crop provided crop} to the provided points.
     *
     * @param cropName the name of the crop.
     * @param points   the points to set.
     */
    public void setJobsPoints(@NotNull String cropName, double points) {
        config.set("crops." + cropName + ".addons.jobsReborn.points", points);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link JobsRebornAddon JobsReborn} money for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the JobsReborn money for the crop.
     */
    public double getJobsMoney(@NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".addons.jobsReborn.money", 0);
    }


    /**
     * Sets the {@link JobsRebornAddon JobsReborn} money for the {@link Crop provided crop} to the provided money.
     *
     * @param cropName the name of the crop.
     * @param money    the money to set.
     */
    public void setJobsMoney(@NotNull String cropName, double money) {
        config.set("crops." + cropName + ".addons.jobsReborn.money", money);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link JobsRebornAddon JobsReborn} experience for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the JobsReborn experience for the crop.
     */
    public double getJobsExperience(@NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".addons.jobsReborn.experience", 0);
    }


    /**
     * Sets the {@link JobsRebornAddon JobsReborn} experience for the {@link Crop provided crop} to the provided experience.
     *
     * @param cropName   the name of the crop.
     * @param experience the experience to set.
     */
    public void setJobsExperience(@NotNull String cropName, double experience) {
        config.set("crops." + cropName + ".addons.jobsReborn.experience", experience);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link AureliumAddon Aurelium} experience for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the Aurelium experience for the crop.
     */
    public double getAureliumExperience(@NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".addons.aurelium.experience", 0);
    }


    /**
     * Sets the {@link AureliumAddon Aurelium} experience for the {@link Crop provided crop} to the provided experience.
     *
     * @param cropName   the name of the crop.
     * @param experience the experience to set.
     */
    public void setAureliumExperience(@NotNull String cropName, double experience) {
        config.set("crops." + cropName + ".addons.aurelium.experience", experience);
        cropsConfig.saveConfig();
    }

}