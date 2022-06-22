package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.utils.MessageUtil;
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
        return MessageUtil.colorize(config.getString("crops." + cropName + ".drop.name"));
    }

    public int getCropDropAmount(final @NotNull String cropName) {
        return config.getInt("crops." + cropName + ".drop.amount");
    }

    public double getCropDropChance(final @NotNull String cropName) {
        return config.getDouble("crops." + cropName + ".drop.chance") / 100.0d;
    }

    public boolean isCropEnabled(final @NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".isEnabled");
    }

    @Contract("_ -> new")
    public @NotNull String getSeedDropName(final @NotNull String seedName) {
        return MessageUtil.colorize(config.getString("seeds." + seedName + ".drop.name"));
    }

    public int getSeedDropAmount(final @NotNull String seedName) {
        return config.getInt("seeds." + seedName + ".drop.amount");
    }

    public double getSeedDropChance(final @NotNull String seedName) {
        return config.getDouble("seeds." + seedName + ".drop.chance") / 100.0d;
    }

    public boolean isSeedEnabled(final @NotNull String seedName) {
        return config.getBoolean("seeds." + seedName + ".isEnabled");
    }
}