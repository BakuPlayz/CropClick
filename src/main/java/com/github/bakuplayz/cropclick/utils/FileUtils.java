package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class FileUtils {

    /**
     * It moves a file from one location to another.
     *
     * @param inFile  The file to move
     * @param outFile The file to move the inFile to.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void moveFile(@NotNull File inFile, @NotNull File outFile) {
        try {
            outFile.getParentFile().mkdirs();
            Files.move(inFile.toPath(), outFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_MOVE_FAILED.send(inFile.getName());
        }
    }


    /**
     * It copies the contents of a YAML file to another YAML file.
     *
     * @param config  The FileConfiguration you want to copy.
     * @param section The section to copy the file to.
     */
    public static void copyYamlTo(@NotNull FileConfiguration config, @NotNull ConfigurationSection section) {
        try {
            config.save(section.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_COPY_FAILED.send(config.getName());
        }
    }

}