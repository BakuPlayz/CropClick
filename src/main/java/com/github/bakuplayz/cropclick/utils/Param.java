package com.github.bakuplayz.cropclick.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@EqualsAndHashCode
public final class Param {

    private final @Getter String key;
    private final @Getter Object value;


    public Param(@NotNull String key, @NotNull Object value) {
        this.value = value;
        this.key = key;
    }


    /**
     * This function returns a string representation of the key-value pair, with the param encoding.
     *
     * @return The key and value of the entry.
     */
    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return key + "=" + value;
    }

}