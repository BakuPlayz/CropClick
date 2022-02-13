package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CropsConfig extends Config {

    //TODO: Rework this

    public CropsConfig(final @NotNull CropClick plugin) {
        super("crops.yml", plugin);
    }

    @Contract("_ -> new")
    public @NotNull String getCropDropName(final @NotNull String cropName) {
        return colorize(config.getString("crops." + cropName + ".dropName"));
    }

    public double getCropDropAmount(final @NotNull String cropName) {
        return config.getInt("crops." + cropName + ".dropAmount");
    }

    public double getCropDropChance(final @NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".dropChance") / 100.0d;
    }

    public boolean isCropEnabled(final @NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".isEnabled");
    }

    @Contract("_ -> new")
    public @NotNull String getSeedDropName(final @NotNull String seedName) {
        return colorize(config.getString("seeds." + seedName + ".dropName"));
    }

    public int getSeedDropAmount(final @NotNull String seedName) {
        return config.getInt("seeds." + seedName + ".dropAmount");
    }

    public double getSeedDropChance(final @NotNull String seedName) {
        return config.getDouble("seeds." + seedName + ".dropChance") / 100.0d;
    }

    public boolean isSeedEnabled(final @NotNull String seedName) {
        return config.getBoolean("seeds." + seedName + ".isEnabled");
    }
}