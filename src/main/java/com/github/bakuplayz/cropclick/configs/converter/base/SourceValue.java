package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;


/**
 * A class representing the YAML source value.
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


    /**
     * Creates an {@link SourceValue SourceValue instance} with the key and converter.
     *
     * @param key       the YAML key.
     * @param converter the YAML converter.
     *
     * @return the {@link SourceValue SourceValue instance}.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull SourceValue of(String key, Function<Object, Object> converter) {
        return new SourceValue(key, converter);
    }


    /**
     * Creates an {@link SourceValue SourceValue instance} with the key and without a converter.
     *
     * @param key the YAML key.
     *
     * @return the {@link SourceValue SourceValue instance}.
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull SourceValue of(String key) {
        return new SourceValue(key, null);
    }


    /**
     * Gets the value stored in the {@link SourceValue} using functions.
     *
     * @param source the source section/path to the value.
     *
     * @return the value stored in the {@link SourceValue}.
     */
    @Override
    public @Nullable Object get(@NotNull ConfigurationSection source) {
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