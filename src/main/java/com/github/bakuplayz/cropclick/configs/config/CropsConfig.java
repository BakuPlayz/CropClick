package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
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

    // TODO: Check through all the comments... I cannot be bothered right now.


    public CropsConfig(@NotNull CropClick plugin) {
        super(plugin, "crops.yml");
    }


    /**
     * This function returns true if the crop exists in the config, and false if it doesn't.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean doesCropExist(@NotNull String name) {
        return config.get("crops." + name) != null;
    }


    /**
     * Returns the name of the crop drop item.
     *
     * @param name The name of the crop.
     *
     * @return The drop name.
     */
    @Contract("_ -> new")
    public @NotNull String getCropDropName(@NotNull String name) {
        return MessageUtils.colorize(config.getString("crops." + name + ".drop.name", name));
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
     * It sets the chance of a crop dropping an item.
     *
     * @param name   The name of the crop.
     * @param chance The chance of the crop dropping the item.
     */
    public void setCropDropChance(@NotNull String name, double chance) {
        config.set("crops." + name + ".drop.chance", chance);
        saveConfig();
    }


    /**
     * Returns whether the crop should drop at least one item when harvested.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("unused")
    public boolean getCropDropAtLeastOne(@NotNull String name) {
        return config.getBoolean("crops." + name + ".drop.atLeastOne", true);
    }


    /**
     * Sets the crop's drop atLeastOne value.
     *
     * @param name       The name of the crop.
     * @param atLeastOne If true, the crop will always drop at least one item.
     */
    public void setCropDropAtLeastOne(@NotNull String name, boolean atLeastOne) {
        config.set("crops." + name + ".drop.atLeastOne", atLeastOne);
        saveConfig();
    }


    /**
     * Returns whether the crop with the given name is harvestable.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean isCropHarvestable(@NotNull String name) {
        return config.getBoolean("crops." + name + ".isHarvestable", true);
    }


    /**
     * It toggles the crop's harvestable state.
     *
     * @param name The name of the crop.
     */
    public void toggleHarvestCrop(@NotNull String name) {
        boolean isEnabled = isCropHarvestable(name);
        config.set("crops." + name + ".isHarvestable", !isEnabled);
        saveConfig();
    }


    /**
     * Returns whether the crop with the given name is linkable.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean isCropLinkable(@NotNull String name) {
        return config.getBoolean("crops." + name + ".isLinkable", true);
    }


    /**
     * It toggles the crop's linkable state.
     *
     * @param name The name of the crop.
     */
    public void toggleLinkableCrop(@NotNull String name) {
        boolean isEnabled = isCropLinkable(name);
        config.set("crops." + name + ".isLinkable", !isEnabled);
        saveConfig();
    }


    /**
     * It gets the list of particles that are used for the crop with the given name.
     *
     * @param name The name of the crop.
     *
     * @return A list of particles, as a string list.
     */
    public @NotNull List<String> getCropParticles(@NotNull String name) {
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
    public @NotNull List<String> getCropSounds(@NotNull String name) {
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
    public int getMcMMOExperience(@NotNull String name) {
        return config.getInt("crops." + name + ".addons.mcMMO.experience", 0);
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
     * It gets the mcMMO experience reason for the crop with the given name.
     *
     * @param name The name of the crop.
     *
     * @return The message that is displayed when a player receives mcMMO experience.
     */
    @Contract("_ -> new")
    public @NotNull String getMcMMOExperienceReason(@NotNull String name) {
        return MessageUtils.colorize(
                config.getString(
                        "crops." + name + ".addons.mcMMO.experienceReason",
                        "Harvested " + name
                )
        );
    }


    /**
     * Sets the mcMMO experience reason for the crop with the given name.
     *
     * @param name   The name of the crop.
     * @param reason The reason to send to the player when they harvest the crop.
     */
    public void setMcMMOExperienceReason(@NotNull String name, @NotNull String reason) {
        config.set("crops." + name + ".addons.mcMMO.experienceReason", reason);
        saveConfig();
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
     * Returns true if the seed with the given name exists in the config.
     *
     * @param name The name of the seed.
     *
     * @return A boolean value.
     */
    public boolean doesSeedExist(@NotNull String name) {
        return config.get("seeds." + name) != null;
    }


    /**
     * Returns the name of the seed drop for the given seed name.
     *
     * @param name The name of the seed.
     *
     * @return A string
     */
    @Contract("_ -> new")
    public @NotNull String getSeedDropName(@NotNull String name) {
        return MessageUtils.colorize(config.getString("seeds." + name + ".drop.name", name));
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
     * Sets the chance of a seed dropping from a mob
     *
     * @param name   The name of the seed.
     * @param chance The chance that the seed will drop.
     */
    public void setSeedDropChance(@NotNull String name, double chance) {
        config.set("seeds." + name + ".drop.chance", chance);
        saveConfig();
    }


    /**
     * It sets the seed's enabled state to the value of the boolean parameter
     *
     * @param name    The name of the seed.
     * @param enabled Whether the seed is enabled.
     */
    public void setSeedEnabled(@NotNull String name, boolean enabled) {
        config.set("seeds." + name + ".isEnabled", enabled);
        saveConfig();
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
        setSeedEnabled(name, !isEnabled);
    }

}