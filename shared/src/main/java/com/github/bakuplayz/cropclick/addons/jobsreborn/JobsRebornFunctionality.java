/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.addons.jobsreborn;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.bakuplayz.cropclick.addons.AddonFunctionality;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import lombok.AllArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class JobsRebornFunctionality implements AddonFunctionality {

    private final static Job FARMER_JOB = Jobs.getJob("Farmer");


    private final CropsConfig config;


    /**
     * Updates the {@link JobsRebornAddon JobsReborn} farmer stats for the {@link Player provided player} based on the {@link Crop provided crop}.
     *
     * @param player the player to update stats for.
     * @param crop   the crop to base the update on.
     */
    public void updateStats(@NotNull OfflinePlayer player, @NotNull Crop crop) {
        JobsPlayer jobsPlayer = new JobsPlayer(player.getName());
        if (!jobsPlayer.isInJob(FARMER_JOB)) {
            return;
        }

        String cropName = crop.getName();
        jobsPlayer.addPoints(config.get(ConfigurationKey.JOBS_POINTS, cropName));
        jobsPlayer.getJobProgression(FARMER_JOB).addExperience(config.get(ConfigurationKey.JOBS_EXPERIENCE, cropName));
        jobsPlayer.getPaymentLimit().addNewAmount(CurrencyType.MONEY, config.get(ConfigurationKey.JOBS_MONEY, cropName));

        Jobs.getBBManager().ShowJobProgression(jobsPlayer);
    }

}
