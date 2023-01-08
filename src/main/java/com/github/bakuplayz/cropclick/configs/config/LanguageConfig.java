package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the YAML file: 'language.yml'.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class LanguageConfig extends Config {

    public LanguageConfig(@NotNull CropClick plugin) {
        super(plugin, "language.yml");
    }


    /**
     * Gets the message from the {@link LanguageConfig language config}.
     *
     * @param category    the category used to find the message.
     * @param subcategory the subcategory used to find the message.
     * @param key         the key used to find the message.
     * @param colorize    whether to colorize the message.
     *
     * @return the found message.
     */
    public @NotNull String getMessage(@NotNull String category,
                                      @NotNull String subcategory,
                                      @NotNull String key,
                                      boolean colorize) {
        String errorMessage = subcategory.equals("title") ? "&cError" : "&cError: Message is null!";
        String message = config.getString(category + "." + subcategory + "." + key, errorMessage);
        return colorize ? MessageUtils.colorize(message) : message;
    }

}