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
 * A utility class for {@link LanguageAPI displayed messages} and or {@link String strings}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MessageUtils {

    /**
     * Translates the given message to a colorized message, using {@link ChatColor}.
     *
     * @param message the message to color.
     *
     * @return the message in color.
     */
    @Contract("_ -> new")
    public static @NotNull String colorize(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    /**
     * Beautifies the provided message by splitting the message into words, capitalizing each of them and joining them back into sentences.
     *
     * @param message       the message to beautify.
     * @param isUnderscored the matcher for underscored messages.
     *
     * @return the beautified message.
     */
    public static @NotNull String beautify(@NotNull String message, boolean isUnderscored) {
        String[] words = message.split(isUnderscored ? "_" : "(?=\\p{Lu})");
        return Arrays.stream(words)
                     .map(String::toLowerCase)
                     .map(StringUtils::capitalize)
                     .collect(Collectors.joining(" "));
    }


    /**
     * "Readifies" the provided message according to the words per line.
     *
     * @param message      the message to readify.
     * @param wordsPerLine the amount of words per line.
     *
     * @return the readified message as a {@link List<String> list of strings}.
     */
    public static @NotNull List<String> readify(@NotNull String message, int wordsPerLine) {
        String[] words = message.split(" ");
        StringBuilder partOfWord = new StringBuilder();
        List<String> readableWords = new ArrayList<>();

        String color = getLastColor(message);
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
     * Gets the enabled message or disabled message, depending on the value of isEnabled.
     *
     * @param plugin    the CropClick instance.
     * @param isEnabled the enabled status.
     *
     * @return the status message.
     */
    public static @NotNull String getStatusMessage(@NotNull CropClick plugin, boolean isEnabled) {
        return isEnabled ? LanguageAPI.Menu.GENERAL_ENABLED_STATUS.get(plugin)
                         : LanguageAPI.Menu.GENERAL_DISABLED_STATUS.get(plugin);
    }


    /**
     * Gets the color used in the last sentence.
     *
     * @param message the message to find the color in.
     *
     * @return the color found.
     */
    private static @NotNull String getLastColor(@NotNull String message) {
        int indexOfColor = message.lastIndexOf('&');
        if (indexOfColor == -1) {
            return "";
        }
        return message.substring(indexOfColor, indexOfColor + 2);
    }

}