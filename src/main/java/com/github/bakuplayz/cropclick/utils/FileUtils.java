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

package com.github.bakuplayz.cropclick.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * A utility class for {@link File files}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class FileUtils {


    /**
     * Moves the {@link File source file} to the {@link File target file}.
     *
     * @param source      the source file.
     * @param target      the target file.
     * @param ignoreError true if errors should be ignored, otherwise false.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void move(@NotNull File source, @NotNull File target, boolean ignoreError) {
        try {
            target.getParentFile().mkdirs();
            Files.move(source.toPath(), target.toPath());
        } catch (IOException e) {
            if (ignoreError) {
                return;
            }
            e.printStackTrace();
        }
    }


    /**
     * Copies the contents of the {@link FileConfiguration YAML configuration} to the {@link ConfigurationSection provided section}.
     *
     * @param config  the config to copy.
     * @param section the section to copy it to.
     */
    public static void copyYamlTo(@NotNull FileConfiguration config,
                                  @NotNull ConfigurationSection section) {
        for (String key : section.getKeys(true)) {
            config.set(key, section.get(key));
        }
    }

}