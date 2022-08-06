package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class MessageUtils {

    /**
     * This function takes a string and returns a string.
     *
     * @param message The message to colorize.
     *
     * @return A string.
     */
    @Contract("_ -> new")
    public static @NotNull String colorize(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    /**
     * It splits the message into words, capitalizes each word, and joins them back together.
     *
     * @param message       The message to beautify.
     * @param isUnderscored If the message is underscored, set this to true.
     *
     * @return A string.
     */
    public static @NotNull String beautify(@NotNull String message, boolean isUnderscored) {
        String[] words = message.split(isUnderscored ? "_" : "(?=\\p{Lu})");
        return Arrays.stream(words)
                     .map(String::toLowerCase)
                     .map(StringUtils::capitalize)
                     .collect(Collectors.joining(" "));
    }


    /**
     * It takes a string and splits it into an array of words, then it takes those words and puts them into a list of
     * strings, each string being a line of the message.
     *
     * @param message      The message you want to readify.
     * @param wordsPerLine The amount of words per line.
     *
     * @return A list of strings
     */
    public static @NotNull List<String> readify(@NotNull String message, int wordsPerLine) {
        String[] words = message.split(" ");
        StringBuilder partOfWord = new StringBuilder();
        List<String> readableWords = new ArrayList<>();

        String color = message.indexOf('&') > -1 ? message.substring(0, 2) : "";

        for (int i = 0; i < words.length; ++i) {
            boolean isNotStart = i != 0;
            boolean isNewLine = i % wordsPerLine == 0;
            boolean skipFirstLine = i != wordsPerLine;
            if (isNotStart && isNewLine) {
                readableWords.add(
                        skipFirstLine
                        ? color + partOfWord
                        : partOfWord.toString()
                );
                partOfWord = new StringBuilder();
            }

            boolean hasNextWord = words.length > (i + 1);
            boolean isNextLine = i % wordsPerLine == (wordsPerLine - 1);
            boolean shouldAddSpace = hasNextWord && !isNextLine;
            partOfWord.append(words[i]).append(shouldAddSpace ? " " : "");
        }

        if (partOfWord.length() != 0) {
            readableWords.add(color + partOfWord);
        }

        return readableWords.size() != 0
               ? readableWords
               : Collections.singletonList(partOfWord.toString());
    }


    /**
     * It returns a string that says "Enabled" or "Disabled" depending on the boolean value passed to it.
     *
     * @param plugin    The plugin instance.
     * @param isEnabled This is a boolean that determines whether the crop is enabled or not.
     *
     * @return A string.
     */
    public static @NotNull String getEnabledStatus(@NotNull CropClick plugin, boolean isEnabled) {
        if (isEnabled) {
            return LanguageAPI.Menu.GENERAL_ENABLED_STATUS.get(plugin);
        }
        return LanguageAPI.Menu.GENERAL_DISABLED_STATUS.get(plugin);
    }

}