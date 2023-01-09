/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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