package com.github.bakuplayz.cropclick.configs;

/**
 * An interface for handling YAML data fetching and data saving.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Configurable {

    /**
     * Reloads the configuration according to the implementing object.
     */
    void reloadConfig();

    /**
     * Saves the configuration according to the implementing object.
     */
    void saveConfig();

}