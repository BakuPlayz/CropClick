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