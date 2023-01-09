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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing a exact/constant value.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ExactValue implements YamlValueProvider {

    private final Object value;


    public ExactValue(Object value) {
        this.value = value;
    }


    /**
     * Creates an {@link ExactValue ExactValue instance} with the value.
     *
     * @param value the value to instantiate.
     *
     * @return the value as an {@link ExactValue}.
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ExactValue of(Object value) {
        return new ExactValue(value);
    }


    /**
     * Gets the {@link #value} stored in the {@link ExactValue}.
     *
     * @param source the source section/path to the value.
     *
     * @return the value stored in the {@link ExactValue}.
     */
    @Override
    public Object get(ConfigurationSection source) {
        return value;
    }

}