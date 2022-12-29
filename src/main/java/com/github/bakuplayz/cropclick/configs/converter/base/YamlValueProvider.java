package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;


/**
 * An interface used to convert YAML values to {@link Object objects}.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public interface YamlValueProvider {

    /**
     * Gets the configuration value based on the configuration section.
     *
     * @param source the source section/path to the value.
     *
     * @return the value found at the section/path.
     */
    Object get(ConfigurationSection source);

}