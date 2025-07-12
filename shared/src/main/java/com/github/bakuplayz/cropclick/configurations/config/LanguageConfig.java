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

package com.github.bakuplayz.cropclick.configurations.config;

import com.github.bakuplayz.cropclick.common.Messages;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYamlKey;
import dev.bakuplayz.spigotstore.persistence.yaml.impl.AbstractPersistentYaml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class representing the YAML file: 'language.yml'.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class LanguageConfig extends AbstractPersistentYaml {

    public LanguageConfig() {
        super("language.yml");
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
    @NotNull
    public String getMessage(@NotNull String category, @NotNull String subcategory, @NotNull String key, boolean colorize) {
        String message = getString(ConfigurationKey.LANGUAGE_KEY, category, subcategory, key);
        return colorize ? Messages.colorize(message) : message;
    }


    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ConfigurationKey implements PersistentYamlKey {

        LANGUAGE_KEY("%s.%s.%s", "&cError: Message is null!");

        @NotNull
        private final String path;

        @Nullable
        private final Object defaultValue;

    }

}