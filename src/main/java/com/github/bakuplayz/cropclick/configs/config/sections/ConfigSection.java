package com.github.bakuplayz.cropclick.configs.config.sections;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the base configuration section.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class ConfigSection {


    /**
     * The configuration file associated with the configuration section.
     */
    protected final FileConfiguration config;


    protected ConfigSection(@NotNull FileConfiguration config) {
        this.config = config;
    }

}