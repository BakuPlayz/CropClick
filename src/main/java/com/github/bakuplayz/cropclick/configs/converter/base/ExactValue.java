package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ExactValue implements YamlValueProvider {

    private final Object value;


    public ExactValue(Object value) {
        this.value = value;
    }


    public static ExactValue of(Object value) {
        return new ExactValue(value);
    }


    @Override
    public Object get(ConfigurationSection source) {
        return value;
    }

}