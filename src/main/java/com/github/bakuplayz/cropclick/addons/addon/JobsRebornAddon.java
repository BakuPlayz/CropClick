package com.github.bakuplayz.cropclick.addons.addon;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class JobsRebornAddon extends Addon {

    private final static Job FARMER_JOB = Jobs.getJob("Farmer");

    public JobsRebornAddon(@NotNull AddonsConfig config) {
        super("JobsReborn", config);
    }

    public void updateStats(@NotNull Player player) {
        JobsPlayer jobsPlayer = new JobsPlayer(player.getName());
        if (!jobsPlayer.isInJob(JobsRebornAddon.FARMER_JOB)) return;

        jobsPlayer.addPoints(getPoints());
        jobsPlayer.getJobProgression(JobsRebornAddon.FARMER_JOB).addExperience(getExperience());
        jobsPlayer.getPaymentLimit().addNewAmount(CurrencyType.MONEY, getMoney());

        Jobs.getBBManager().ShowJobProgression(jobsPlayer);
    }

    private double getPoints() {
        return addonsConfig.getConfig().getDouble("jobsReborn.<cropName>.points");
    }

    private double getExperience() {
        return addonsConfig.getConfig().getDouble("jobsReborn.<cropName>.experience");
    }

    private double getMoney() {
        return addonsConfig.getConfig().getDouble("jobsReborn.<cropName>.money");
    }

}
