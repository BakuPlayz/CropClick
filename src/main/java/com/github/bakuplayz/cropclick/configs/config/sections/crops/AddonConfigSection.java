package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
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


    public AddonConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
    }


    /**
     * It gets the mcMMO experience value, from the config.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of mcMMO experience that is given when the crop is harvested.
     */
    public int getMcMMOExperience(@NotNull String cropName) {
        return config.getInt("crops." + cropName + ".addons.mcMMO.experience", 0);
    }


    /**
     * It sets the mcMMO experience value for a crop, in the config.
     *
     * @param cropName The name of the crop.
     * @param amount   The amount of experience to give.
     */
    public void setMcMMOExperience(@NotNull String cropName, double amount) {
        config.set("crops." + cropName + ".addons.mcMMO.experience", amount);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the mcMMO experience reason for the crop with the given name, from the config.
     *
     * @param cropName The name of the crop.
     *
     * @return The message that is displayed when a player receives mcMMO experience.
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
     * It sets the mcMMO experience reason for the crop with the given name, in the config.
     *
     * @param cropName The name of the crop.
     * @param reason   The experience reason to send to the player when they harvest the crop.
     */
    public void setMcMMOExperienceReason(@NotNull String cropName, @NotNull String reason) {
        config.set("crops." + cropName + ".addons.mcMMO.experienceReason", reason);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the points for the specified crop from the config.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of points the player will receive for harvesting the specified crop.
     */
    public double getJobsPoints(@NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".addons.jobsReborn.points", 0);
    }


    /**
     * It sets the amount of points a player will get for harvesting a crop, to the config.
     *
     * @param cropName The name of the crop.
     * @param amount   The amount of points to give to the player.
     */
    public void setJobsPoints(@NotNull String cropName, double amount) {
        config.set("crops." + cropName + ".addons.jobsReborn.points", amount);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the money value for the specified crop, from the config.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of money that the player will receive when they harvest the specified crop.
     */
    public double getJobsMoney(@NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".addons.jobsReborn.money", 0);
    }


    /**
     * It sets the amount of money a player will get for harvesting a crop, to the config.
     *
     * @param cropName The name of the crop.
     * @param amount   The amount of money to give when the specified crop, is harvested.
     */
    public void setJobsMoney(@NotNull String cropName, double amount) {
        config.set("crops." + cropName + ".addons.jobsReborn.money", amount);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the Jobs experience value for the specified crop, from the config.
     *
     * @param cropName The name of the crop.
     *
     * @return The experience gained from harvesting the specified crop.
     */
    public double getJobsExperience(@NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".addons.jobsReborn.experience", 0);
    }


    /**
     * It sets the Jobs experience amount for the specified crop, to the config.
     *
     * @param cropName The name of the crop.
     * @param amount   The amount of experience to give to the player.
     */
    public void setJobsExperience(@NotNull String cropName, double amount) {
        config.set("crops." + cropName + ".addons.jobsReborn.experience", amount);
        cropsConfig.saveConfig();
    }


}