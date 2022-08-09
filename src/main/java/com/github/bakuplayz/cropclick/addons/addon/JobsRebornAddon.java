package com.github.bakuplayz.cropclick.addons.addon;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class JobsRebornAddon extends Addon {

    private final Job farmerJob;

    private final CropsConfig cropsConfig;


    public JobsRebornAddon(@NotNull CropClick plugin) {
        super(plugin, "JobsReborn");
        this.cropsConfig = plugin.getCropsConfig();
        this.farmerJob = Jobs.getJob("Farmer");
    }


    /**
     * It adds points, experience, and money to the player's JobsReborn account.
     *
     * @param player The player who is being updated.
     */
    public void updateStats(@NotNull Player player, @NotNull Crop crop) {
        JobsPlayer jobsPlayer = new JobsPlayer(player.getName());
        if (!jobsPlayer.isInJob(farmerJob)) {
            return;
        }

        String cropName = crop.getName();
        jobsPlayer.addPoints(getPoints(cropName));
        jobsPlayer.getJobProgression(farmerJob).addExperience(getExperience(cropName));
        jobsPlayer.getPaymentLimit().addNewAmount(CurrencyType.MONEY, getMoney(cropName));

        Jobs.getBBManager().ShowJobProgression(jobsPlayer);
    }


    /**
     * Get the points of the crop with the passed name.
     *
     * @param name The name of the crop.
     *
     * @return The points of the crop.
     */
    private double getPoints(@NotNull String name) {
        return cropsConfig.getJobsPoints(name);
    }


    /**
     * Get the experience of a crop with the passed name.
     *
     * @param name The name of the crop.
     *
     * @return The experience of the crop.
     */
    private double getExperience(@NotNull String name) {
        return cropsConfig.getJobsExperience(name);
    }


    /**
     * Get the money of a crop with the passed name.
     *
     * @param name The name of the crop.
     *
     * @return The money of the crop.
     */
    private double getMoney(@NotNull String name) {
        return cropsConfig.getJobsMoney(name);
    }

}