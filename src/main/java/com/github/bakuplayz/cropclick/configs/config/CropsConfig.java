package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.utils.MessageUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CropsConfig extends Config {

    //TODO: Check through all the comments.. I cannot be bothered right now.


    public CropsConfig(@NotNull CropClick plugin) {
        super("crops.yml", plugin);
    }


    /**
     * Returns the name of the crop drop item.
     *
     * @param name The name of the crop.
     *
     * @return The drop name.
     */
    @NotNull
    @Contract("_ -> new")
    public String getCropDropName(@NotNull String name) {
        return MessageUtil.colorize(config.getString("crops." + name + ".drop.name", name));
    }


    /**
     * It sets the name of the drop of a crop.
     *
     * @param name    The name of the crop.
     * @param newName The new name of the crop.
     */
    public void setCropDropName(@NotNull String name, @NotNull String newName) {
        config.set("crops." + name + ".drop.name", newName);
        saveConfig();
    }


    /**
     * Get the amount of items that a crop will drop when harvested.
     *
     * @param name The name of the crop.
     *
     * @return The amount of items that will be dropped when the crop is harvested.
     */
    public int getCropDropAmount(@NotNull String name) {
        return config.getInt("crops." + name + ".drop.amount", 0);
    }


    /**
     * It sets the amount of items that a crop will drop when harvested.
     *
     * @param name   The name of the crop.
     * @param amount The amount of the item that will be dropped.
     */
    public void setCropDropAmount(@NotNull String name, int amount) {
        config.set("crops." + name + ".drop.amount", amount);
        saveConfig();
    }


    /**
     * Adds the given amount to the current drop amount of the given crop, but never more than 576.
     *
     * @param name   The name of the crop.
     * @param amount The amount to add to the current drop amount.
     */
    public void addCropDropAmount(@NotNull String name, int amount) {
        int oldAmount = getCropDropAmount(name) + amount;
        int newAmount = Math.min(oldAmount, 576);
        setCropDropAmount(name, newAmount);
    }


    /**
     * Remove the given amount from the given crop's drop amount, but never less than zero.
     *
     * @param name   The name of the crop.
     * @param amount The amount to add to the current amount.
     */
    public void removeCropDropAmount(@NotNull String name, int amount) {
        int oldAmount = getCropDropAmount(name) - amount;
        int newAmount = Math.max(oldAmount, 0);
        setCropDropAmount(name, newAmount);
    }


    /**
     * Get the chance of a crop dropping an item.
     *
     * @param name The name of the crop.
     *
     * @return The chance of a crop dropping an item.
     */
    public double getCropDropChance(@NotNull String name) {
        return config.getDouble("crops." + name + ".drop.chance", 0.0) / 100.0d;
    }


    /**
     * Returns whether the crop with the given name is enabled.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean isCropEnabled(@NotNull String name) {
        return config.getBoolean("crops." + name + ".isEnabled", true);
    }


    /**
     * It toggles the crop's enabled state.
     *
     * @param name The name of the crop.
     */
    public void toggleCrop(@NotNull String name) {
        boolean isEnabled = isCropEnabled(name);
        config.set("crops." + name + ".isEnabled", !isEnabled);
        saveConfig();
    }


    /**
     * It gets the list of particles that are used for the crop with the given name.
     *
     * @param name The name of the crop.
     *
     * @return A list of particles, as a string list.
     */
    @NotNull
    public List<String> getCropParticles(@NotNull String name) {
        return config.getStringList("crops." + name + ".particles");
    }


    /**
     * If the particle is in the list of particles, remove it, otherwise add it.
     *
     * @param name     The name of the crop.
     * @param particle The name of the particle you want to toggle.
     */
    public void toggleParticleCrop(@NotNull String name, @NotNull String particle) {
        List<String> particles = getCropParticles(name);
        if (particles.contains(particle)) {
            particles.remove(particle);
        } else {
            particles.add(particle);
        }
        config.set("crops." + name + ".particles", particles);
        saveConfig();
    }


    /**
     * Get the list of sounds that the crop with the given name should play.
     *
     * @param name The name of the crop.
     *
     * @return A list of particles, as a string list.
     */
    @NotNull
    public List<String> getCropSounds(@NotNull String name) {
        return config.getStringList("crops." + name + ".sounds");
    }


    /**
     * If the sound is in the list of sounds, remove it, otherwise add it.
     *
     * @param name  The name of the crop.
     * @param sound The name of the sound to toggle.
     */
    public void toggleSoundCrop(@NotNull String name, @NotNull String sound) {
        List<String> sounds = getCropSounds(name);
        if (sounds.contains(sound)) {
            sounds.remove(sound);
        } else {
            sounds.add(sound);
        }
        config.set("crops." + name + ".sounds", sounds);
        saveConfig();
    }


    /**
     * Returns whether the crop should replant itself after being harvested.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean shouldReplantCrop(@NotNull String name) {
        return config.getBoolean("crops." + name + ".shouldReplant", true);
    }


    /**
     * Toggle the value of the shouldReplant key for the crop with the given name.
     *
     * @param name The name of the crop.
     */
    public void toggleReplantCrop(@NotNull String name) {
        boolean shouldReplant = shouldReplantCrop(name);
        config.set("crops." + name + ".shouldReplant", !shouldReplant);
        saveConfig();
    }


    /**
     * Returns whether the crop should drop at least one item.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean shouldDropAtLeastOne(@NotNull String name) {
        return config.getBoolean("crops." + name + ".atLeastOne", true);
    }


    /**
     * It toggles the dropAtLeastOne boolean for the crop with the given name.
     *
     * @param name The name of the crop.
     */
    public void toggleDropAtLeastOne(@NotNull String name) {
        boolean dropAtLeastOne = shouldDropAtLeastOne(name);
        config.set("crops." + name + ".atLeastOne", !dropAtLeastOne);
        saveConfig();
    }


    /**
     * It gets the mcMMO experience value from the config.
     *
     * @param name The name of the crop.
     *
     * @return The amount of mcMMO experience that is given when the crop is harvested.
     */
    public double getMcMMOExperience(@NotNull String name) {
        return config.getDouble("crops." + name + ".addons.mcMMO.experience", 0);
    }


    /**
     * It sets the mcMMO experience for a crop.
     *
     * @param name   The name of the crop.
     * @param amount The amount of experience to give.
     */
    public void setMcMMOExperience(@NotNull String name, double amount) {
        config.set("crops." + name + ".addons.mcMMO.experience", amount);
        saveConfig();
    }


    /**
     * Adds the given amount of experience to the given skill, but caps it at 10,000.
     *
     * @param name   The name of the skill to add experience to.
     * @param amount The amount of experience to add.
     */
    public void addMcMMOExperience(@NotNull String name, int amount) {
        double oldAmount = getMcMMOExperience(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        setMcMMOExperience(name, newAmount);
    }


    /**
     * Removes the specified amount of experience from the specified skill.
     *
     * @param name   The name of the skill to remove experience from.
     * @param amount The amount of experience to remove.
     */
    public void removeMcMMOExperience(@NotNull String name, int amount) {
        double oldAmount = getMcMMOExperience(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        setMcMMOExperience(name, newAmount);
    }


    /**
     * Get the points for the specified crop.
     *
     * @param name The name of the crop.
     *
     * @return The amount of points the player will receive for harvesting the specified crop.
     */
    public double getJobsPoints(@NotNull String name) {
        return config.getDouble("crops." + name + ".addons.jobsReborn.points", 0);
    }


    /**
     * It sets the amount of points a player will get for harvesting a crop.
     *
     * @param name   The name of the crop.
     * @param amount The amount of points to give to the player.
     */
    public void setJobsPoints(@NotNull String name, double amount) {
        config.set("crops." + name + ".addons.jobsReborn.points", amount);
        saveConfig();
    }


    /**
     * Adds the specified amount of points to the crop.
     *
     * @param name   The name of the crop.
     * @param amount The amount of points to add.
     */
    public void addJobsPoints(@NotNull String name, int amount) {
        double oldAmount = getJobsPoints(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        setJobsPoints(name, newAmount);
    }


    /**
     * Removes the specified amount of points from the specified player.
     *
     * @param name   The name of the player.
     * @param amount The amount of points to remove.
     */
    public void removeJobsPoints(@NotNull String name, int amount) {
        double oldAmount = getJobsPoints(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        setJobsPoints(name, newAmount);
    }


    /**
     * Get the money value for the specified crop.
     *
     * @param name The name of the crop.
     *
     * @return The amount of money that the player will receive when they harvest the specified crop.
     */
    public double getJobsMoney(@NotNull String name) {
        return config.getDouble("crops." + name + ".addons.jobsReborn.money", 0);
    }


    /**
     * It sets the amount of money a player will get for harvesting a crop.
     *
     * @param name   The name of the crop.
     * @param amount The amount of money to give when the specified crop, is harvested.
     */
    public void setJobsMoney(@NotNull String name, double amount) {
        config.set("crops." + name + ".addons.jobsReborn.money", amount);
        saveConfig();
    }


    /**
     * Adds the specified amount of money to the crop.
     *
     * @param name   The name of the crop.
     * @param amount The amount of money to add to the specified crop.
     */
    public void addJobsMoney(@NotNull String name, int amount) {
        double oldAmount = getJobsMoney(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        setJobsMoney(name, newAmount);
    }


    /**
     * Remove money from a player's Jobs account.
     *
     * @param name   The name of the player
     * @param amount The amount of money to remove from the player's balance.
     */
    public void removeJobsMoney(@NotNull String name, int amount) {
        double oldAmount = getJobsMoney(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        setJobsMoney(name, newAmount);
    }


    /**
     * Get the experience value for the specified crop.
     *
     * @param name The name of the crop.
     *
     * @return The experience gained from harvesting the specified crop.
     */
    public double getJobsExperience(@NotNull String name) {
        return config.getDouble("crops." + name + ".addons.jobsReborn.experience", 0);
    }


    /**
     * It sets the experience amount for the specified crop
     *
     * @param name   The name of the crop.
     * @param amount The amount of experience to give to the player.
     */
    public void setJobsExperience(@NotNull String name, double amount) {
        config.set("crops." + name + ".addons.jobsReborn.experience", amount);
        saveConfig();
    }


    /**
     * Adds the given amount of experience to the given job, but caps it at 10,000.
     *
     * @param name   The name of the job.
     * @param amount The amount of experience to add.
     */
    public void addJobsExperience(@NotNull String name, int amount) {
        double oldAmount = getJobsExperience(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        setJobsExperience(name, newAmount);
    }


    /**
     * Removes the specified amount of experience from the specified job.
     *
     * @param name   The name of the job you want to remove experience from.
     * @param amount The amount of experience to remove.
     */
    public void removeJobsExperience(@NotNull String name, int amount) {
        double oldAmount = getJobsExperience(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        setJobsExperience(name, newAmount);
    }


    /**
     * Returns the name of the seed drop for the given seed name.
     *
     * @param name The name of the seed.
     *
     * @return A string
     */
    @NotNull
    @Contract("_ -> new")
    public String getSeedDropName(@NotNull String name) {
        return MessageUtil.colorize(config.getString("seeds." + name + ".drop.name", name));
    }


    /**
     * Sets the name of the item that is dropped when a seed is broken.
     *
     * @param name    The name of the seed.
     * @param newName The new name of the seed.
     */
    public void setSeedDropName(@NotNull String name, @NotNull String newName) {
        config.set("seeds." + name + ".drop.name", newName);
        saveConfig();
    }


    /**
     * Get the amount of seeds that should be dropped when a plant is harvested.
     *
     * @param name The name of the seed.
     *
     * @return The amount of seeds that will drop from a plant.
     */
    public int getSeedDropAmount(@NotNull String name) {
        return config.getInt("seeds." + name + ".drop.amount", 0);
    }


    /**
     * Sets the amount of seeds that drop from a plant.
     *
     * @param name   The name of the seed.
     * @param amount The amount of seeds to drop.
     */
    public void setSeedDropAmount(@NotNull String name, int amount) {
        config.set("seeds." + name + ".drop.amount", amount);
        saveConfig();
    }


    /**
     * Adds the given amount to the seed drop amount of the given seed, but never more than 576.
     *
     * @param name   The name of the seed.
     * @param amount The amount of seeds to add to the current amount.
     */
    public void addSeedDropAmount(@NotNull String name, int amount) {
        int oldAmount = getSeedDropAmount(name) + amount;
        int newAmount = Math.min(oldAmount, 576);
        setSeedDropAmount(name, newAmount);
    }


    /**
     * Removes the given amount from the seed drop amount of the given seed, but never less than zero.
     *
     * @param name   The name of the seed.
     * @param amount The amount to add to the current amount.
     */
    public void removeSeedDropAmount(@NotNull String name, int amount) {
        int oldAmount = getSeedDropAmount(name) - amount;
        int newAmount = Math.max(oldAmount, 0);
        setSeedDropAmount(name, newAmount);
    }


    /**
     * Get the chance of a seed dropping from a mob.
     *
     * @param name The name of the seed.
     *
     * @return The chance of a seed dropping.
     */
    public double getSeedDropChance(@NotNull String name) {
        return config.getDouble("seeds." + name + ".drop.chance", 0.0) / 100.0d;
    }


    /**
     * Returns whether the seed with the given name is enabled.
     *
     * @param name The name of the seed.
     *
     * @return A boolean value.
     */
    public boolean isSeedEnabled(@NotNull String name) {
        return config.getBoolean("seeds." + name + ".isEnabled", true);
    }


    /**
     * Toggle the seed with the given name.
     *
     * @param name The name of the seed.
     */
    public void toggleSeed(@NotNull String name) {
        boolean isEnabled = isSeedEnabled(name);
        config.set("seeds." + name + ".isEnabled", !isEnabled);
        saveConfig();
    }

}