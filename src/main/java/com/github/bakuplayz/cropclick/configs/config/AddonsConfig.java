/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.Config;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the YAML file: 'addons.yml'.
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
     * Checks whether the {@link Addon addon} is installed based on the provided name.
     *
     * @param addonName the name of the addon.
     *
     * @return true if installed, otherwise false.
     */
    public boolean isInstalled(@NotNull String addonName) {
        return Bukkit.getPluginManager().isPluginEnabled(addonName);
    }


    /**
     * Checks whether the {@link Addon addon} is enabled based on the provided name.
     *
     * @param addonName the name of the addon.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled(@NotNull String addonName) {
        return config.getBoolean("addons." + addonName + ".isEnabled", true);
    }


    /**
     * Toggles the {@link Addon addon} based on the provided name.
     *
     * @param addonName the name of the addon.
     */
    public void toggleAddon(@NotNull String addonName) {
        config.set("addons." + addonName + ".isEnabled", !isEnabled(addonName));
        saveConfig();
    }

}