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

    private final CropsConfig cropsConfig;


    public SeedConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
    }


    /**
     * It returns true if the seed, with the given name, exists in the config.
     *
     * @param seedName The name of the seed.
     *
     * @return The existing state of the seed in the config.
     */
    public boolean doesExist(@NotNull String seedName) {
        return config.get("seeds." + seedName) != null;
    }


    /**
     * It returns the name of the seed's drop or seed name, if the drop name is not existent.
     *
     * @param seedName The name of the seed.
     *
     * @return The drop name.
     */
    @Contract("_ -> new")
    public @NotNull String getDropName(@NotNull String seedName) {
        return MessageUtils.colorize(config.getString("seeds." + seedName + ".drop.name", seedName));
    }


    /**
     * It sets the name of the drop to the given name.
     *
     * @param seedName The name of the seed.
     * @param newName  The new name of the seed.
     */
    public void setDropName(@NotNull String seedName, @NotNull String newName) {
        config.set("seeds." + seedName + ".drop.name", newName);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the amount of drop that should be dropped when a crop is harvested.
     *
     * @param seedName The name of the seed.
     *
     * @return The amount of drop that will be dropped.
     */
    public int getDropAmount(@NotNull String seedName) {
        return getDropAmount(seedName, 0);
    }


    /**
     * It gets the amount of drop that should be dropped when a crop is harvested.
     *
     * @param seedName The name of the seed.
     * @param def      The default value to return if the value is not found.
     *
     * @return The amount of drop that will be dropped.
     */
    public int getDropAmount(@NotNull String seedName, int def) {
        return config.getInt("seeds." + seedName + ".drop.amount", def);
    }


    /**
     * It sets the amount of drop should be dropped when a crop is harvested.
     *
     * @param seedName The name of the seed.
     * @param amount   The amount of the drop that should drop.
     */
    public void setDropAmount(@NotNull String seedName, int amount) {
        config.set("seeds." + seedName + ".drop.amount", amount);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the chance of a seed dropping drops (as decimal eg. 0.2).
     *
     * @param seedName The name of the seed.
     *
     * @return The chance of a seed dropping drops (as decimal).
     */
    public double getDropChance(@NotNull String seedName) {
        return getDropChance(seedName, 0.0);
    }


    /**
     * It gets the chance of a seed dropping drops (as decimal eg. 0.2).
     *
     * @param seedName The name of the seed.
     * @param def      The default value to return if the config doesn't have the value.
     *
     * @return The chance of a seed dropping drops (as decimal).
     */
    public double getDropChance(@NotNull String seedName, double def) {
        return config.getDouble("seeds." + seedName + ".drop.chance", def) / 100.0d;
    }


    /**
     * It sets the chance of a seed dropping drops (as decimal eg. 0.2).
     *
     * @param seedName The name of the seed.
     * @param chance   The chance of a seed dropping drops (as decimal).
     */
    public void setDropChance(@NotNull String seedName, double chance) {
        config.set("seeds." + seedName + ".drop.chance", chance);
        cropsConfig.saveConfig();
    }


    /**
     * It sets the seed's enabled state, with the given name, to the given value.
     *
     * @param seedName The name of the seed.
     * @param enabled  Whether the seed is enabled.
     */
    public void setEnabled(@NotNull String seedName, boolean enabled) {
        config.set("seeds." + seedName + ".isEnabled", enabled);
        cropsConfig.saveConfig();
    }


    /**
     * It returns the seed's, with the given name, enabled state.
     *
     * @param seedName The name of the seed.
     *
     * @return The enabled state of the seed.
     */
    public boolean isEnabled(@NotNull String seedName) {
        return config.getBoolean("seeds." + seedName + ".isEnabled", true);
    }


    /**
     * It toggles the seed's, with the given name, enabled state.
     *
     * @param seedName The name of the seed.
     */
    public void toggle(@NotNull String seedName) {
        setEnabled(seedName, !isEnabled(seedName));
    }

}