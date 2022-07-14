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
 */
public final class LanguageConfig extends Config {

    public LanguageConfig(@NotNull CropClick plugin) {
        super("language.yml", plugin);
    }

    public @NotNull String getMessage(String category, String subCategory, String key) {
        String message = config.getString(
                category + "." + subCategory + "." + key,
                "&cError: Message is null!"
        );
        return MessageUtil.colorize(message);
    }

}