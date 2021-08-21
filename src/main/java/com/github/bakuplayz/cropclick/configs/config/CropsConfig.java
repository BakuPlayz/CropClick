package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CropsConfig extends Config {

    //rework this

    public CropsConfig(final @NotNull CropClick plugin) {
        super("crops.yml", plugin);
    }

    @Contract("_ -> new")
    public @NotNull String getCropDropName(final @NotNull String cropName) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("crops." + cropName + ".dropName"));
    }

    public double getCropDropAmount(final @NotNull String cropName) {
        return getConfig().getInt("crops." + cropName + ".dropAmount");
    }

    public double getCropDropChance(final @NotNull String cropName) {
        return getConfig().getDouble("crops." + cropName + ".dropChance") / 100.0d;
    }

    public boolean isCropEnabled(final @NotNull String cropName) {
        return getConfig().getBoolean("crops." + cropName + ".isEnabled");
    }

    @Contract("_ -> new")
    public @NotNull String getSeedDropName(final @NotNull String seedName) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("seeds." + seedName + ".dropName"));
    }

    public int getSeedDropAmount(final @NotNull String seedName) {
        return getConfig().getInt("seeds." + seedName + ".dropAmount");
    }

    public double getSeedDropChance(final @NotNull String seedName) {
        return getConfig().getDouble("seeds." + seedName + ".dropChance") / 100.0d;
    }

    public boolean isSeedEnabled(final @NotNull String seedName) {
        return getConfig().getBoolean("seeds." + seedName + ".isEnabled");
    }
}
