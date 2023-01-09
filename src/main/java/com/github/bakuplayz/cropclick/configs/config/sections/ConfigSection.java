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

package com.github.bakuplayz.cropclick.configs.config.sections;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the base configuration section.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class ConfigSection {


    /**
     * The configuration file associated with the configuration section.
     */
    protected final FileConfiguration config;


    protected ConfigSection(@NotNull FileConfiguration config) {
        this.config = config;
    }

}