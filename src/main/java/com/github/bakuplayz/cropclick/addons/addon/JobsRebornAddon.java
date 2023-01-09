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

package com.github.bakuplayz.cropclick.addons.addon;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the <a href="https://www.spigotmc.org/resources/jobs-reborn.4216/">JobsReborn</a> addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class JobsRebornAddon extends Addon {

    private final Job farmerJob;

    private final AddonConfigSection addonSection;


    public JobsRebornAddon(@NotNull CropClick plugin) {
        super(plugin, "JobsReborn");
        this.farmerJob = Jobs.getJob("Farmer");
        this.addonSection = plugin.getCropsConfig().getAddonSection();
    }


    /**
     * Updates the {@link JobsRebornAddon JobsReborn} farmer stats for the {@link Player provided player} based on the {@link Crop provided crop}.
     *
     * @param player the player to update stats for.
     * @param crop   the crop to base the update on.
     */
    public void updateStats(@NotNull Player player, @NotNull Crop crop) {
        JobsPlayer jobsPlayer = new JobsPlayer(player.getName());
        if (!jobsPlayer.isInJob(farmerJob)) {
            return;
        }

        String cropName = crop.getName();
        jobsPlayer.addPoints(getPoints(cropName));
        jobsPlayer.getJobProgression(farmerJob)
                  .addExperience(getExperience(cropName));
        jobsPlayer.getPaymentLimit()
                  .addNewAmount(CurrencyType.MONEY, getMoney(cropName));

        Jobs.getBBManager().ShowJobProgression(jobsPlayer);
    }


    /**
     * Gets the {@link JobsRebornAddon JobsRebon} points for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the points for the crop.
     */
    private double getPoints(@NotNull String cropName) {
        return addonSection.getJobsPoints(cropName);
    }


    /**
     * Gets the {@link JobsRebornAddon JobsRebon} experience for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the experience for the crop.
     */
    private double getExperience(@NotNull String cropName) {
        return addonSection.getJobsExperience(cropName);
    }


    /**
     * Gets the {@link JobsRebornAddon JobsRebon} money for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the money for the crop.
     */
    private double getMoney(@NotNull String cropName) {
        return addonSection.getJobsMoney(cropName);
    }

}