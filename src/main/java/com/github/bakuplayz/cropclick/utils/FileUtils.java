package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.language.LanguageAPI;
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
            LanguageAPI.Console.FILE_MOVE_FAILED.send(source.getName());
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