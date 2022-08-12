package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SeedConfigSection extends ConfigSection {

    // TODO: Check through all the comments... I cannot be bothered right now.

    private final CropsConfig cropsConfig;


    public SeedConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
    }


    /**
     * Returns true if the seed with the given name exists in the config.
     *
     * @param seedName The name of the seed.
     *
     * @return A boolean value.
     */
    public boolean doesExist(@NotNull String seedName) {
        return config.get("seeds." + seedName) != null;
    }


    /**
     * Returns the name of the seed drop for the given seed name.
     *
     * @param seedName The name of the seed.
     *
     * @return A string
     */
    @Contract("_ -> new")
    public @NotNull String getDropName(@NotNull String seedName) {
        return MessageUtils.colorize(config.getString("seeds." + seedName + ".drop.name", seedName));
    }


    /**
     * Sets the name of the item that is dropped when a seed is broken.
     *
     * @param seedName The name of the seed.
     * @param newName  The new name of the seed.
     */
    public void setDropName(@NotNull String seedName, @NotNull String newName) {
        config.set("seeds." + seedName + ".drop.name", newName);
        cropsConfig.saveConfig();
    }


    /**
     * Get the amount of seeds that should be dropped when a plant is broken.
     *
     * @param seedName The name of the seed.
     *
     * @return The amount of seeds that will drop from a plant.
     */
    public int getDropAmount(@NotNull String seedName) {
        return getDropAmount(seedName, 0);
    }


    /**
     * Get the amount of seeds that should drop from a plant.
     *
     * @param seedName The name of the seed.
     * @param def      The default value to return if the value is not found.
     *
     * @return The amount of seeds that will drop from a plant.
     */
    public int getDropAmount(@NotNull String seedName, int def) {
        return config.getInt("seeds." + seedName + ".drop.amount", def);
    }


    /**
     * Sets the amount of seeds that drop from a plant.
     *
     * @param seedName The name of the seed.
     * @param amount   The amount of seeds to drop.
     */
    public void setDropAmount(@NotNull String seedName, int amount) {
        config.set("seeds." + seedName + ".drop.amount", amount);
        cropsConfig.saveConfig();
    }


    /**
     * Get the chance of a seed dropping from a mob.
     *
     * @param seedName The name of the seed.
     *
     * @return The chance of a seed dropping.
     */
    public double getDropChance(@NotNull String seedName) {
        return getDropChance(seedName, 0.0);
    }


    /**
     * Get the chance of a seed dropping from a plant, as a decimal.
     *
     * @param seedName The name of the seed.
     * @param def      The default value to return if the config doesn't have the value.
     *
     * @return The chance of a seed dropping.
     */
    public double getDropChance(@NotNull String seedName, double def) {
        return config.getDouble("seeds." + seedName + ".drop.chance", def) / 100.0d;
    }


    /**
     * Sets the chance of a seed dropping.
     *
     * @param seedName The name of the seed.
     * @param chance   The chance of the seed dropping.
     */
    public void setDropChance(@NotNull String seedName, double chance) {
        config.set("seeds." + seedName + ".drop.chance", chance);
        cropsConfig.saveConfig();
    }


    /**
     * It sets the seed's enabled state to the value of the boolean parameter
     *
     * @param seedName The name of the seed.
     * @param enabled  Whether the seed is enabled.
     */
    public void setEnabled(@NotNull String seedName, boolean enabled) {
        config.set("seeds." + seedName + ".isEnabled", enabled);
        cropsConfig.saveConfig();
    }


    /**
     * Returns whether the seed with the given name is enabled.
     *
     * @param seedName The name of the seed.
     *
     * @return A boolean value.
     */
    public boolean isEnabled(@NotNull String seedName) {
        return config.getBoolean("seeds." + seedName + ".isEnabled", true);
    }


    /**
     * Toggle the seed with the given name.
     *
     * @param seedName The name of the seed.
     */
    public void toggle(@NotNull String seedName) {
        setEnabled(seedName, !isEnabled(seedName));
    }

}