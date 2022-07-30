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
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class Config {

    protected CropClick plugin;

    private File file;
    private final String fileName;

    protected @Getter FileConfiguration config;


    public Config(@NotNull String fileName, @NotNull CropClick plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
    }


    /**
     * If the file doesn't exist, create it and copy the default file from the jar. If it does exist, load it.
     */
    public void setup() {
        this.file = new File(plugin.getDataFolder(), fileName);

        try {
            if (file.createNewFile()) {
                plugin.saveResource(fileName, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SETUP_FAILED.send(fileName);
        } finally {
            this.config = YamlConfiguration.loadConfiguration(file);
            LanguageAPI.Console.FILE_SETUP_LOAD.send(fileName);
        }
    }


    /**
     * Reloads the config file.
     */
    public void reloadConfig() {
        if (config == null || file == null) return;

        this.config = YamlConfiguration.loadConfiguration(file);
        LanguageAPI.Console.FILE_RELOAD.send(fileName);
    }


    /**
     * It saves the config file.
     */
    public void saveConfig() {
        if (config == null || file == null) return;

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SAVE_FAILED.send(fileName);
        }
    }

}