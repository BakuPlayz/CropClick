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

import java.util.ArrayList;
import java.util.List;


/**
 * A class representing an YAML converter.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class YamlConverter {

    /**
     * A variable containing all the YAML conversions.
     */
    private final List<YamlConversion> conversions;


    public YamlConverter() {
        conversions = new ArrayList<>();
    }


    /**
     * Adds a conversion to the {@link #conversions conversions list}.
     *
     * @param conversion the given conversion.
     */
    public void addConversion(YamlConversion conversion) {
        conversions.add(conversion);
    }


    /**
     * Adds a conversion to the {@link #conversions conversions list}.
     *
     * @param provider   the converter for the YAML objects.
     * @param targetPath the path where the objects are found.
     */
    public void addConversion(YamlValueProvider provider, String targetPath) {
        addConversion(new YamlConversion(provider, targetPath));
    }


    /**
     * Executes all the conversions found in the {@link #conversions conversions list}.
     *
     * @param source the source section/path.
     * @param target the target section/path.
     */
    public void execute(ConfigurationSection source, ConfigurationSection target) {
        for (YamlConversion conversion : conversions) {
            conversion.execute(source, target);
        }
    }

}