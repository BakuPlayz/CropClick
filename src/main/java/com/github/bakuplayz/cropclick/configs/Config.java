package com.github.bakuplayz.cropclick.configs;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    protected CropClick plugin;

    private File file;
    private final String FILE_NAME;
    private @Getter FileConfiguration config;

    public Config(final @NotNull String fileName,
                  final @NotNull CropClick plugin) {
        this.FILE_NAME = fileName;
        this.plugin = plugin;
    }

    public void setup() {
        file = new File(plugin.getDataFolder(), FILE_NAME);

        try {
            if (file.createNewFile()) {
                plugin.saveResource(FILE_NAME, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SETUP_FAILED.sendMessage(FILE_NAME);
        } finally {
            config = YamlConfiguration.loadConfiguration(file);
            LanguageAPI.Console.FILE_SETUP_LOAD.sendMessage(FILE_NAME);
        }
    }

    public void reloadConfig() {
        if (config == null || file == null) return;

        config = YamlConfiguration.loadConfiguration(file);
        LanguageAPI.Console.FILE_RELOAD.sendMessage(FILE_NAME);
    }
}
