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