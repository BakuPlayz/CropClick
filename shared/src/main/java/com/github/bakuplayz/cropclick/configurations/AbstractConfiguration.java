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

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.common.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * A class representing a YAML file.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */

public abstract class AbstractConfiguration implements Configuration {

    protected final CropClick plugin;

    protected final String fileName;

    @Setter(AccessLevel.PRIVATE)
    private File file;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private FileConfiguration configuration;


    public AbstractConfiguration(@NotNull CropClick plugin, @NotNull String fileName) {
        this.fileName = fileName;
        this.plugin = plugin;

        create();
    }


    @Override
    public String getString(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getStringOrDefault(key, (String) key.getDefaultValue(), args);
    }


    @Override
    public String getStringOrDefault(@NotNull ConfigurationKey key, String def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return getConfiguration().getString(path, def);
    }


    @Override
    public Object getObject(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getObjectOrDefault(key, key.getDefaultValue(), args);
    }


    @Override
    public Object getObjectOrDefault(@NotNull ConfigurationKey key, Object def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return getConfiguration().get(path, key.getDefaultValue());
    }


    @Override
    public double getDouble(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getDoubleOrDefault(key, (double) key.getDefaultValue(), args);
    }


    @Override
    public double getDoubleOrDefault(@NotNull ConfigurationKey key, double def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return getConfiguration().getDouble(path, def);
    }


    @Override
    public float getFloat(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getFloatOrDefault(key, (float) key.getDefaultValue(), args);
    }


    @Override
    public float getFloatOrDefault(@NotNull ConfigurationKey key, float def, @NotNull String... args) {
        return (float) getDoubleOrDefault(key, def, args);
    }


    @Override
    public long getLong(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getLongOrDefault(key, (long) key.getDefaultValue(), args);
    }


    @Override
    public long getLongOrDefault(@NotNull ConfigurationKey key, long def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return getConfiguration().getLong(path, def);
    }


    @Override
    public int getInt(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getIntOrDefault(key, (int) key.getDefaultValue(), args);
    }


    @Override
    public int getIntOrDefault(@NotNull ConfigurationKey key, int def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return getConfiguration().getInt(path, def);
    }


    @Override
    public boolean getBoolean(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getBooleanOrDefault(key, (boolean) key.getDefaultValue(), args);
    }


    @Override
    public boolean getBooleanOrDefault(@NotNull ConfigurationKey key, boolean def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return getConfiguration().getBoolean(path, def);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getListOrDefault(key, (List<T>) key.getDefaultValue(), args);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getListOrDefault(@NotNull ConfigurationKey key, @NotNull List<T> def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return (List<T>) getConfiguration().getList(path, def);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T getEnum(@NotNull ConfigurationKey key, @NotNull Class<T> enumClass, @NotNull String... args) {
        return getEnumOrDefault(key, enumClass, (T) key.getDefaultValue(), args);
    }


    @Override
    public <T extends Enum<T>> T getEnumOrDefault(@NotNull ConfigurationKey key, @NotNull Class<T> enumClass, @NotNull T def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        String value = getConfiguration().getString(path);

        if (value != null) {
            try {
                return Enum.valueOf(enumClass, value.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
                Log.severe("Invalid enum value, fallbacks to default.");
            }
        }

        return def;
    }


    @Override
    public Location getLocation(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getLocationOrDefault(key, (Location) key.getDefaultValue(), args);
    }


    @Override
    public Location getLocationOrDefault(@NotNull ConfigurationKey key, @Nullable Location def, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return (Location) configuration.get(path, def);
    }


    @Override
    public boolean isNull(@NotNull ConfigurationKey key, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        return getConfiguration().get(path) == null;
    }


    @NotNull
    @Override
    public Set<String> getKeys(@NotNull ConfigurationKey key, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        ConfigurationSection section = getConfiguration().getConfigurationSection(path);
        return section == null ? Collections.emptySet() : section.getKeys(false);
    }


    @Override
    public <T> void set(@NotNull ConfigurationKey key, T data, @NotNull String... args) {
        setWithoutSave(key, data, args);
        save();
    }


    @Override
    public <T> void setWithoutSave(@NotNull ConfigurationKey key, T data, @NotNull String... args) {
        String path = Strings.replace(key.getPath(), "%s", args);
        getConfiguration().set(path, data);
    }


    @Override
    public <T> void setWithReload(@NotNull ConfigurationKey key, T data, @NotNull String... args) {
        set(key, data, args);
        setConfiguration(YamlConfiguration.loadConfiguration(file));
    }


    @Override
    public int countKeys(@NotNull ConfigurationKey key, @NotNull String... args) {
        return getKeys(key, args).size();
    }


    /**
     * Creates the configuration, iff not present.
     */
    @Override
    public void create() {
        setFile(getNewFileInstance());
        setConfiguration(YamlConfiguration.loadConfiguration(file));

        try {
            if (file.createNewFile()) {
                plugin.saveResource(fileName, true);
            }
        } catch (IOException exception) {
            Log.severe("Could not setup {0}.", fileName);
        } finally {
            Log.info("Loading {0}.", fileName);
        }
    }


    /**
     * Reloads the configuration.
     */
    @Override
    public void reload() {
        setFile(getNewFileInstance());
        setConfiguration(YamlConfiguration.loadConfiguration(file));
        Log.info("Reloading {0}.", fileName);
    }


    /**
     * Saves the configuration to it's dedicated file.
     */
    @Override
    public void save() {
        try {
            getConfiguration().save(file);
        } catch (IOException exception) {
            Log.severe("Could not save {0}.", fileName);
        }
    }


    /**
     * Resets the configuration.
     */
    @Override
    public void reset() {
        try {
            Files.deleteIfExists(file.toPath());
            create();
        } catch (IOException exception) {
            Log.severe("Could not reset {0}.", fileName);
        }
    }


    @NotNull
    private File getNewFileInstance() {
        return new File(plugin.getDataFolder(), fileName);
    }

}