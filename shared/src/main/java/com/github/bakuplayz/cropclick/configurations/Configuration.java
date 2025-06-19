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

package com.github.bakuplayz.cropclick.configurations;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * An interface for handling YAML data fetching and data saving.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Configuration {

    String getString(@NotNull ConfigurationKey key, @NotNull String... args);


    String getStringOrDefault(@NotNull ConfigurationKey key, String def, @NotNull String... args);


    Object getObject(@NotNull ConfigurationKey key, @NotNull String... args);


    Object getObjectOrDefault(@NotNull ConfigurationKey key, Object def, @NotNull String... args);


    double getDouble(@NotNull ConfigurationKey key, @NotNull String... args);


    double getDoubleOrDefault(@NotNull ConfigurationKey key, double def, @NotNull String... args);


    float getFloat(@NotNull ConfigurationKey key, @NotNull String... args);


    float getFloatOrDefault(@NotNull ConfigurationKey key, float def, @NotNull String... args);


    long getLong(@NotNull ConfigurationKey key, @NotNull String... args);


    long getLongOrDefault(@NotNull ConfigurationKey key, long def, @NotNull String... args);


    int getInt(@NotNull ConfigurationKey key, @NotNull String... args);


    int getIntOrDefault(@NotNull ConfigurationKey key, int def, @NotNull String... args);


    boolean getBoolean(@NotNull ConfigurationKey key, @NotNull String... args);


    boolean getBooleanOrDefault(@NotNull ConfigurationKey key, boolean def, @NotNull String... args);


    <T> List<T> getList(@NotNull ConfigurationKey key, @NotNull String... args);


    <T> List<T> getListOrDefault(@NotNull ConfigurationKey key, List<T> def, @NotNull String... args);


    <T extends Enum<T>> T getEnum(@NotNull ConfigurationKey key, @NotNull Class<T> enumClass, @NotNull String... args);


    <T extends Enum<T>> T getEnumOrDefault(@NotNull ConfigurationKey key, @NotNull Class<T> enumClass, @NotNull T def, @NotNull String... args);


    Location getLocation(@NotNull ConfigurationKey key, @NotNull String... args);


    Location getLocationOrDefault(@NotNull ConfigurationKey key, @Nullable Location def, @NotNull String... args);


    boolean isNull(@NotNull ConfigurationKey key, @NotNull String... args);


    @NotNull
    Set<String> getKeys(@NotNull ConfigurationKey key, @NotNull String... args);


    <T> void set(@NotNull ConfigurationKey key, T data, @NotNull String... args);


    <T> void setWithoutSave(@NotNull ConfigurationKey key, T data, @NotNull String... args);


    <T> void setWithReload(@NotNull ConfigurationKey key, T data, @NotNull String... args);


    int countKeys(@NotNull ConfigurationKey key, @NotNull String... args);


    /**
     * Creates the configuration according to the implementing object.
     */
    void create();


    /**
     * Reloads the configuration according to the implementing object.
     */
    void reload();


    /**
     * Saves the configuration according to the implementing object.
     */
    void save();


    /**
     * Resets the configuration according to the implementing object.
     */
    void reset();


}