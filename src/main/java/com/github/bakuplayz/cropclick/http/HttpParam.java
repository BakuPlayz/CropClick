package com.github.bakuplayz.cropclick.http;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing <a href="https://developer.mozilla.org/en-US/docs/Web/API/URL/searchParams">URL params</a>.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@EqualsAndHashCode
public final class HttpParam {

    private final @Getter String key;
    private final @Getter Object value;


    public HttpParam(@NotNull String key, @NotNull Object value) {
        this.value = value;
        this.key = key;
    }


    /**
     * Gets the param as a string representation.
     *
     * @return the param as a string.
     */
    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return key + "=" + value;
    }

}