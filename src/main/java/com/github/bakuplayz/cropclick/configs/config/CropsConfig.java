package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Sound;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Arrays;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
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
        return getCropDropAmount(name, 0);
    }


    /**
     * Get the amount of items that a crop will drop when harvested.
     *
     * @param name The name of the crop.
     * @param def  The default value to return if the value is not found.
     *
     * @return The amount of items that will be dropped when the crop is harvested.
     */
    public int getCropDropAmount(@NotNull String name, int def) {
        return config.getInt("crops." + name + ".drop.amount", def);
    }


    /**
     * It sets the amount of items that a crop will drop when harvested
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
        return getCropDropChance(name, 0.0);
    }


    /**
     * Get the chance of a crop dropping its item.
     *
     * @param name The name of the crop.
     * @param def  The default value to return if the config doesn't have the value.
     *
     * @return The chance of a crop dropping an item.
     */
    public double getCropDropChance(@NotNull String name, double def) {
        return config.getDouble("crops." + name + ".drop.chance", def) / 100.0d;
    }


    /**
     * Sets the chance of a crop dropping an item.
     *
     * @param name   The name of the crop.
     * @param chance The chance that the crop will drop the item.
     */
    public void setCropDropChance(@NotNull String name, double chance) {
        config.set("crops." + name + ".drop.chance", chance);
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
     * Set the crop harvestable state of the crop with the given name to the given value.
     *
     * @param name          The name of the crop.
     * @param isHarvestable Whether the crop can be harvested.
     */
    public void setCropHarvestable(@NotNull String name, boolean isHarvestable) {
        config.set("crops." + name + ".isHarvestable", isHarvestable);
        saveConfig();
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
        return isCropLinkable(name, true);
    }


    /**
     * Returns whether the crop with the given name is linkable.
     *
     * @param name The name of the crop.
     * @param def  The default value to return if the parameter is not found.
     *
     * @return A boolean value.
     */
    public boolean isCropLinkable(@NotNull String name, boolean def) {
        return config.getBoolean("crops." + name + ".isLinkable", def);
    }


    /**
     * It sets the crop linkable value to the value of the linkable variable
     *
     * @param name     The name of the crop.
     * @param linkable Whether the crop can be linked to a crop.
     */
    public void setCropLinkable(@NotNull String name, boolean linkable) {
        config.set("crops." + name + ".isLinkable", linkable);
        saveConfig();
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
     * Returns true if the particle is enabled for the crop (aka has one or more amount).
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return A boolean value.
     */
    public boolean isParticleEnabled(@NotNull String cropName, @NotNull String particleName) {
        return getParticleAmount(cropName, particleName) != 0;
    }


    /**
     * Return the amount of enabled particles for a crop.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of enabled particles for a crop.
     */
    public int getAmountOfEnabledParticles(@NotNull String cropName) {
        return (int) Arrays.stream(ParticleEffect.values())
                           .map(ParticleEffect::name)
                           .filter(particle -> isParticleEnabled(cropName, particle))
                           .count();
    }


    /**
     * Get the delay between particle spawns for a specific particle type for a specific crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The delay of the particle.
     */
    public double getParticleDelay(@NotNull String cropName, @NotNull String particleName) {
        return config.getDouble("crops." + cropName + ".particles." + particleName + ".delay", 0.0);
    }


    /**
     * Sets the delay of a particle for a crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param delay        The delay between each particle spawn.
     */
    public void setParticleDelay(@NotNull String cropName, @NotNull String particleName, double delay) {
        config.set("crops." + cropName + ".particles." + particleName + ".delay", delay);
        saveConfig();
    }


    /**
     * Get the speed of a particle.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The speed of the particle.
     */
    public double getParticleSpeed(@NotNull String cropName, @NotNull String particleName) {
        return config.getDouble("crops." + cropName + ".particles." + particleName + ".speed", 0.0);
    }


    /**
     * Sets the speed of a particle for a crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param speed        The speed of the particle.
     */
    public void setParticleSpeed(@NotNull String cropName, @NotNull String particleName, double speed) {
        config.set("crops." + cropName + ".particles." + particleName + ".speed", speed);
        saveConfig();
    }


    /**
     * Get the amount of particles to spawn for a specific particle type.
     *
     * @param cropName     The cropName of the crop.
     * @param particleName The name of the particle.
     *
     * @return The amount of particles.
     */
    public int getParticleAmount(@NotNull String cropName, @NotNull String particleName) {
        return config.getInt("crops." + cropName + ".particles." + particleName + ".amount", 0);
    }


    /**
     * It sets the amount of particles that will be spawned when a crop is harvested
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param amount       The amount of particles to spawn.
     */
    public void setParticleAmount(@NotNull String cropName, @NotNull String particleName, int amount) {
        config.set("crops." + cropName + ".particles." + particleName + ".amount", amount);
        saveConfig();
    }


    /**
     * Returns true if the sound is enabled, false otherwise.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return A boolean value.
     */
    public boolean isSoundEnabled(@NotNull String cropName, @NotNull String soundName) {
        double volume = getSoundVolume(cropName, soundName);
        double pitch = getSoundPitch(cropName, soundName);
        return volume != 0.0 & pitch != 0.0;
    }


    /**
     * Return the amount of enabled sounds for a given crop.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of enabled sounds for a crop.
     */
    public int getAmountOfEnabledSounds(@NotNull String cropName) {
        return (int) Arrays.stream(Sound.values())
                           .map(Sound::name)
                           .filter(sound -> isSoundEnabled(cropName, sound))
                           .count();
    }


    /**
     * Get the delay between the sound being played and the next sound being played.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The delay of the sound.
     */
    public double getSoundDelay(@NotNull String cropName, @NotNull String soundName) {
        return config.getDouble("crops." + cropName + ".sounds." + soundName + ".delay", 0.0);
    }


    /**
     * Sets the delay of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound you want to set the delay for.
     * @param delay     The delay between each sound.
     */
    public void setSoundDelay(@NotNull String cropName, @NotNull String soundName, double delay) {
        config.set("crops." + cropName + ".sounds." + soundName + ".delay", delay);
        saveConfig();
    }


    /**
     * Get the volume of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The volume of the sound.
     */
    public double getSoundVolume(@NotNull String cropName, @NotNull String soundName) {
        return config.getDouble("crops." + cropName + ".sounds." + soundName + ".volume", 0.0);
    }


    /**
     * Sets the volume of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     * @param volume    The volume of the sound.
     */
    public void setSoundVolume(@NotNull String cropName, @NotNull String soundName, double volume) {
        config.set("crops." + cropName + ".sounds." + soundName + ".volume", volume);
        saveConfig();
    }


    /**
     * Get the pitch of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The pitch of the sound.
     */
    public double getSoundPitch(@NotNull String cropName, @NotNull String soundName) {
        return config.getDouble("crops." + cropName + ".sounds." + soundName + ".pitch", 0.0);
    }


    /**
     * Sets the pitch of the sound for the specified crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     * @param pitch     The pitch of the sound.
     */
    public void setSoundPitch(@NotNull String cropName, @NotNull String soundName, double pitch) {
        config.set("crops." + cropName + ".sounds." + soundName + ".pitch", pitch);
        saveConfig();
    }


    /**
     * Returns whether the crop should replant itself after being harvested.
     *
     * @param name The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean shouldCropReplant(@NotNull String name) {
        return config.getBoolean("crops." + name + ".shouldReplant", true);
    }


    /**
     * Sets the replant value of a crop to the given value.
     *
     * @param name    The name of the crop.
     * @param replant Whether the crop should replant itself
     */
    public void setCropReplant(@NotNull String name, boolean replant) {
        config.set("crops." + name + ".shouldReplant", replant);
        saveConfig();
    }


    /**
     * Toggle the value of the shouldReplant key for the crop with the given name.
     *
     * @param name The name of the crop.
     */
    public void toggleReplantCrop(@NotNull String name) {
        setCropReplant(name, !shouldCropReplant(name));
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
     * Sets the crop's drop atLeastOne value.
     *
     * @param name       The name of the crop.
     * @param atLeastOne If true, the crop will always drop at least one item.
     */
    public void setDropAtLeastOne(@NotNull String name, boolean atLeastOne) {
        config.set("crops." + name + ".drop.atLeastOne", atLeastOne);
        saveConfig();
    }


    /**
     * It toggles the dropAtLeastOne boolean for the crop with the given name.
     *
     * @param name The name of the crop.
     */
    public void toggleDropAtLeastOne(@NotNull String name) {
        setDropAtLeastOne(name, !shouldDropAtLeastOne(name));
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
     * Get the amount of seeds that should be dropped when a plant is broken.
     *
     * @param name The name of the seed.
     *
     * @return The amount of seeds that will drop from a plant.
     */
    public int getSeedDropAmount(@NotNull String name) {
        return getSeedDropAmount(name, 0);
    }


    /**
     * Get the amount of seeds that should drop from a plant.
     *
     * @param name The name of the seed.
     * @param def  The default value to return if the value is not found.
     *
     * @return The amount of seeds that will drop from a plant.
     */
    public int getSeedDropAmount(@NotNull String name, int def) {
        return config.getInt("seeds." + name + ".drop.amount", def);
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
    @SuppressWarnings("unused")
    public double getSeedDropChance(@NotNull String name) {
        return getSeedDropChance(name, 0.0);
    }


    /**
     * Get the chance of a seed dropping from a plant, as a decimal.
     *
     * @param name The name of the seed.
     * @param def  The default value to return if the config doesn't have the value.
     *
     * @return The chance of a seed dropping.
     */
    public double getSeedDropChance(@NotNull String name, double def) {
        return config.getDouble("seeds." + name + ".drop.chance", def) / 100.0d;
    }


    /**
     * Sets the chance of a seed dropping.
     *
     * @param name   The name of the seed.
     * @param chance The chance of the seed dropping.
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