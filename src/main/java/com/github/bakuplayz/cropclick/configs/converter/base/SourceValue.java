package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SourceValue implements YamlValueProvider {

    private final String key;
    private final Function<Object, Object> converter;


    public SourceValue(String key, Function<Object, Object> converter) {
        this.key = key;
        this.converter = converter;
    }


    public static SourceValue of(String key, Function<Object, Object> converter) {
        return new SourceValue(key, converter);
    }


    public static SourceValue of(String key) {
        return new SourceValue(key, null);
    }


    @Override
    public Object get(ConfigurationSection source) {
        Object sourceObj = source.get(key);
        if (sourceObj == null) {
            return null;
        }
        if (converter == null) {
            return sourceObj;
        }

        return converter.apply(sourceObj);
    }

}