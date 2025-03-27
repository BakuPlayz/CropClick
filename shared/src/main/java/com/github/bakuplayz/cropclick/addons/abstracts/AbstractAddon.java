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

package com.github.bakuplayz.cropclick.addons.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonFunctionality;
import com.github.bakuplayz.cropclick.configurations.config.AddonsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A class representing an addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.2.0
 */
public abstract class AbstractAddon {

    @Getter
    protected final String name;

    protected transient final CropClick plugin;

    protected transient final CropsConfig cropsConfig;

    protected transient final AddonsConfig addonsConfig;

    private transient final List<String> banishedWorlds;


    public AbstractAddon(@NotNull CropClick plugin, @NotNull String name) {
        this.addonsConfig = plugin.getAddonsConfig();
        this.cropsConfig = plugin.getCropsConfig();
        this.banishedWorlds = new ArrayList<>();
        this.plugin = plugin;
        this.name = name;
    }


    public abstract void setup();


    @NotNull
    public abstract AddonFunctionality getFunctionality();


    /**
     * Checks whether the {@link AbstractAddon extending addon} is installed.
     *
     * @return true if installed, otherwise false.
     */
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }


    /**
     * Checks whether the {@link AbstractAddon extending addon} is enabled.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled() {
        return addonsConfig.isEnabled(name);
    }


    /**
     * Gets the amount of {@link FarmWorld farm worlds} where the {@link AbstractAddon extending addon} is banished.
     *
     * @return the amount of worlds where the addon is banished.
     */
    public int getAmountOfBanished() {
        return banishedWorlds.size() + 1;
    }

}