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
    protected @Getter FileConfiguration config;

    public Config(@NotNull String fileName,
                  @NotNull CropClick plugin) {
        this.FILE_NAME = fileName;
        this.plugin = plugin;
    }

    public void setup() {
        this.file = new File(this.plugin.getDataFolder(), this.FILE_NAME);

        try {
            if (this.file.createNewFile()) this.plugin.saveResource(this.FILE_NAME, true);
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SETUP_FAILED.send(this.FILE_NAME);
        } finally {
            this.config = YamlConfiguration.loadConfiguration(this.file);
            LanguageAPI.Console.FILE_SETUP_LOAD.send(this.FILE_NAME);
        }
    }

    public void reloadConfig() {
        if (this.config == null || this.file == null) return;

        this.config = YamlConfiguration.loadConfiguration(this.file);
        LanguageAPI.Console.FILE_RELOAD.send(this.FILE_NAME);
    }

}