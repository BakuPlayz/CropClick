package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the seed configuration section.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SeedConfigSection extends ConfigSection {

    private final CropsConfig cropsConfig;


    public SeedConfigSection(@NotNull CropsConfig config) {
        super(config.getConfig());
        this.cropsConfig = config;
    }


    /**
     * Checks whether the {@link Seed provided seed} exists in the {@link CropsConfig crops config}.
     *
     * @param seedName the name of the seed.
     *
     * @return true if it exists, otherwise false.
     */
    public boolean exists(@NotNull String seedName) {
        return config.get("seeds." + seedName) != null;
    }


    /**
     * Gets the {@link Drop drop} name for the {@link Seed provided seed}.
     *
     * @param seedName the name of the seed.
     *
     * @return the seed's drop name.
     */
    @Contract("_ -> new")
    public @NotNull String getDropName(@NotNull String seedName) {
        return MessageUtils.colorize(config.getString("seeds." + seedName + ".drop.name", seedName));
    }


    /**
     * Sets the {@link Drop drop} name for the {@link Seed provided seed} to the provided name.
     *
     * @param seedName the name of the seed.
     * @param name     the new name to set.
     */
    public void setDropName(@NotNull String seedName, @NotNull String name) {
        config.set("seeds." + seedName + ".drop.name", name);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link Drop drop} amount for the {@link Seed provided seed}.
     *
     * @param seedName the name of the seed.
     *
     * @return the seed's drop amount.
     */
    public int getDropAmount(@NotNull String seedName) {
        return getDropAmount(seedName, 0);
    }


    /**
     * Gets the {@link Drop drop} amount for the {@link Seed provided seed}.
     *
     * @param seedName the name of the seed.
     * @param def      the default amount, if non was found.
     *
     * @return the seed's drop amount, otherwise the default.
     */
    public int getDropAmount(@NotNull String seedName, int def) {
        return config.getInt("seeds." + seedName + ".drop.amount", def);
    }


    /**
     * Sets the {@link Drop drop} amount for the {@link Seed provided seed} to the provided amount.
     *
     * @param seedName the name of the seed.
     * @param amount   the amount to set.
     */
    public void setDropAmount(@NotNull String seedName, int amount) {
        config.set("seeds." + seedName + ".drop.amount", amount);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link Drop drop} chance for the {@link Seed provided seed}.
     *
     * @param seedName the name of the seed.
     *
     * @return the seed's drop chance.
     */
    public double getDropChance(@NotNull String seedName) {
        return getDropChance(seedName, 0.0);
    }


    /**
     * Gets the {@link Drop drop} chance for the {@link Seed provided seed}.
     *
     * @param seedName the name of the seed.
     * @param def      the default chance, if non was found.
     *
     * @return the seed's drop chance, otherwise the default.
     */
    public double getDropChance(@NotNull String seedName, double def) {
        return config.getDouble("seeds." + seedName + ".drop.chance", def) / 100.0d;
    }


    /**
     * Sets the {@link Drop drop} chance for the {@link Seed provided seed} to the provided chance.
     *
     * @param seedName the name of the seed.
     * @param chance   the chance to set.
     */
    public void setDropChance(@NotNull String seedName, double chance) {
        config.set("seeds." + seedName + ".drop.chance", chance);
        cropsConfig.saveConfig();
    }


    /**
     * Sets the enabled state of the {@link Seed provided seed} to the provided state.
     *
     * @param seedName  the name of the seed.
     * @param isEnabled the enabled state to set.
     */
    public void setEnabled(@NotNull String seedName, boolean isEnabled) {
        config.set("seeds." + seedName + ".isEnabled", isEnabled);
        cropsConfig.saveConfig();
    }


    /**
     * Checks whether the {@link Seed provided seed} is enabled.
     *
     * @param seedName the name of the seed.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled(@NotNull String seedName) {
        return config.getBoolean("seeds." + seedName + ".isEnabled", true);
    }


    /**
     * Toggles enabled state of the {@link Seed provided seed}.
     *
     * @param seedName the name of the seed.
     */
    public void toggleSeed(@NotNull String seedName) {
        setEnabled(seedName, !isEnabled(seedName));
    }

}