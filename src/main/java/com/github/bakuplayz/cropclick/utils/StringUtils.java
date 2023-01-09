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

package com.github.bakuplayz.cropclick.utils;

/**
 * A utility class for {@link String strings}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class StringUtils {

    /**
     * Replaces the {@link String provided tag} only once inside the {@link String provided text} with the {@link String replacement}.
     *
     * @param text        the text to replace.
     * @param tag         the tag to replace.
     * @param replacement the replacement to replace the tag.
     *
     * @return the replaced string.
     */
    public static String replace(String text, String tag, String replacement) {
        return org.apache.commons.lang.StringUtils.replaceOnce(text, tag, replacement);
    }

}