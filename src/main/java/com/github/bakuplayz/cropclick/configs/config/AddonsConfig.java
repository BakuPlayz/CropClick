package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AddonsConfig extends Config {

    public AddonsConfig(@NotNull CropClick plugin) {
        super(plugin, "addons.yml");
    }


    /**
     * Returns true if the plugin/addon is installed and enabled, false otherwise.
     *
     * @param name The name of the plugin to check.
     *
     * @return A boolean value.
     */
    public boolean isInstalled(@NotNull String name) {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }


    /**
     * Returns whether the addon is enabled.
     *
     * @param name The name of the addon.
     *
     * @return A boolean value.
     */
    public boolean isEnabled(@NotNull String name) {
        return config.getBoolean("addons." + name + ".isEnabled", true);
    }


    /**
     * It toggles the enabled state of an addon.
     *
     * @param name The name of the addon.
     */
    public void toggle(@NotNull String name) {
        boolean isEnabled = isEnabled(name);
        config.set("addons." + name + ".isEnabled", !isEnabled);
        saveConfig();
    }

}