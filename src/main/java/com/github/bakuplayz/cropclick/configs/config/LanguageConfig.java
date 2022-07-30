package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.utils.MessageUtil;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class LanguageConfig extends Config {

    public LanguageConfig(@NotNull CropClick plugin) {
        super("language.yml", plugin);
    }


    /**
     * It gets a message from the config file.
     *
     * @param category    The category of the message.
     * @param subCategory The subcategory of the message.
     * @param key         The key of the message you want to get.
     * @param colorize    Whether to colorize the message.
     *
     * @return A string.
     */
    public @NotNull String getMessage(@NotNull String category,
                                      @NotNull String subCategory,
                                      @NotNull String key,
                                      boolean colorize) {
        String errorMsg = subCategory.equals("title") ? "&cError" : "&cError: Message is null!";
        String message = config.getString(category + "." + subCategory + "." + key, errorMsg);
        return colorize ? MessageUtil.colorize(message) : message;
    }

}