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

package com.github.bakuplayz.cropclick.configs;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;


/**
 * A class representing a YAML file.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class Config implements Configurable {

    protected final CropClick plugin;


    protected @Getter FileConfiguration config;
    private final String fileName;
    private File file;


    public Config(@NotNull CropClick plugin, @NotNull String fileName) {
        this.fileName = fileName;
        this.plugin = plugin;
    }


    /**
     * Sets up the {@link Config extending config}.
     */
    public void setup() {
        this.file = new File(plugin.getDataFolder(), fileName);

        try {
            if (file.createNewFile()) {
                plugin.saveResource(fileName, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SETUP_FAILED.send(plugin.getLogger(), fileName);
        } finally {
            this.config = YamlConfiguration.loadConfiguration(file);
            LanguageAPI.Console.FILE_SETUP_LOAD.send(plugin.getLogger(), fileName);
        }
    }


    /**
     * Reloads the {@link Config extending config}.
     */
    public void reloadConfig() {
        if (file == null) return;
        if (config == null) return;

        this.config = YamlConfiguration.loadConfiguration(file);
        LanguageAPI.Console.FILE_RELOAD.send(plugin.getLogger(), fileName);
    }


    /**
     * Saves the {@link Config extending config}.
     */
    public void saveConfig() {
        if (file == null) return;
        if (config == null) return;

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SAVE_FAILED.send(plugin.getLogger(), fileName);
        }
    }

}