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

    public CropsConfig(@NotNull CropClick plugin) {
        super("crops.yml", plugin);
    }

    @Contract("_ -> new")
    public @NotNull String getCropDropName(@NotNull String name) {
        return MessageUtil.colorize(config.getString("crops." + name + ".drop.name", "null"));
    }

    public int getCropDropAmount(@NotNull String name) {
        return config.getInt("crops." + name + ".drop.amount", 1);
    }

    public double getCropDropChance(@NotNull String name) {
        return config.getDouble("crops." + name + ".drop.chance", 0.0) / 100.0d;
    }

    public boolean isCropEnabled(@NotNull String name) {
        return config.getBoolean("crops." + name + ".isEnabled", true);
    }

    public boolean shouldReplantCrop(String name) {
        return config.getBoolean("crops." + name + ".shouldReplant", true);
    }

    @Contract("_ -> new")
    public @NotNull String getSeedDropName(@NotNull String name) {
        return MessageUtil.colorize(config.getString("seeds." + name + ".drop.name", "null"));
    }

    public int getSeedDropAmount(@NotNull String name) {
        return config.getInt("seeds." + name + ".drop.amount", 1);
    }

    public double getSeedDropChance(@NotNull String name) {
        return config.getDouble("seeds." + name + ".drop.chance", 0.0) / 100.0d;
    }

    public boolean isSeedEnabled(@NotNull String name) {
        return config.getBoolean("seeds." + name + ".isEnabled", true);
    }

}