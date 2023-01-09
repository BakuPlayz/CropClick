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

package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;


/**
 * A class representing a YAML conversion.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class YamlConversion {

    private final YamlValueProvider valueProvider;
    private final String targetPath;


    public YamlConversion(YamlValueProvider valueProvider, String targetPath) {
        this.valueProvider = valueProvider;
        this.targetPath = targetPath;
    }


    /**
     * Executes the YAML conversion.
     *
     * @param source the source section/path.
     * @param target the target section/path.
     */
    public void execute(ConfigurationSection source, ConfigurationSection target) {
        Object obj = valueProvider.get(source);
        if (obj == null) {
            return;
        }

        target.set(targetPath, obj);
    }


}