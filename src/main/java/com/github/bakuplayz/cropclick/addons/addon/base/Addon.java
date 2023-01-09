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

package com.github.bakuplayz.cropclick.addons.addon.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.utils.Enableable;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * A class representing an addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class Addon implements Enableable {

    protected final @Getter String name;

    protected transient final CropClick plugin;
    protected transient final AddonsConfig addonsConfig;

    /**
     * A variable containing all the registered worlds.
     */
    private transient final HashMap<String, FarmWorld> worlds;


    public Addon(@NotNull CropClick plugin, @NotNull String name) {
        this.worlds = plugin.getWorldManager().getWorlds();
        this.addonsConfig = plugin.getAddonsConfig();
        this.plugin = plugin;
        this.name = name;
    }


    /**
     * Checks whether the {@link Addon extending addon} is enabled.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled() {
        return addonsConfig.isEnabled(name);
    }


    /**
     * Gets the amount of {@link FarmWorld farm worlds} where the {@link Addon extending addon} is banished.
     *
     * @return the amount of worlds where the addon is banished.
     */
    public int getAmountOfBanished() {
        return (int) worlds.values().stream()
                           .filter(farmWorld -> farmWorld.getBanishedAddons().contains(this))
                           .count();
    }

}