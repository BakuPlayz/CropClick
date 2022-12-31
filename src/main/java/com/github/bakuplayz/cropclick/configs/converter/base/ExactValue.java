package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing a exact/constant value.
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


    /**
     * Creates an {@link ExactValue ExactValue instance} with the value.
     *
     * @param value the value to instantiate.
     *
     * @return the value as an {@link ExactValue}.
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ExactValue of(Object value) {
        return new ExactValue(value);
    }


    /**
     * Gets the {@link #value} stored in the {@link ExactValue}.
     *
     * @param source the source section/path to the value.
     *
     * @return the value stored in the {@link ExactValue}.
     */
    @Override
    public Object get(ConfigurationSection source) {
        return value;
    }

}