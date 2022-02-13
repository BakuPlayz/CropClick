package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class AddonsConfig extends Config {

    public AddonsConfig(final @NotNull CropClick plugin) {
        super("addons.yml", plugin);
    }

    public boolean isEnabled(final @NotNull String addonName) {
        if (!Bukkit.getPluginManager().isPluginEnabled(addonName)) return false;
        return config.getBoolean("addons." + addonName + ".isEnabled");
    }
}