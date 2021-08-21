package com.github.bakuplayz.cropclick.addons.addon;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.bakuplayz.cropclick.addons.addon.template.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class JobsRebornAddon extends Addon {

    private final Job FARMER_JOB = Jobs.getJob("Farmer");

    public JobsRebornAddon(final @NotNull AddonsConfig addonsConfig) {
        super("JobsReborn", addonsConfig);
    }

    public void updateStats(final @NotNull Player player) {
        JobsPlayer jobsPlayer = new JobsPlayer(player.getName());
        if (!jobsPlayer.isInJob(FARMER_JOB)) return;

        jobsPlayer.addPoints(getPoints());
        jobsPlayer.getJobProgression(FARMER_JOB).addExperience(getExperience());
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
