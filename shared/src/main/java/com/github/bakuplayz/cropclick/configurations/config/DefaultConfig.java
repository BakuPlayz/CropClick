/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.AbstractConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * A class representing the YAML file: 'config.yml'.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class DefaultConfig extends AbstractConfiguration {

    public DefaultConfig(@NotNull CropClick plugin) {
        super(plugin, "config.yml");
    }


    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ConfigurationKey implements com.github.bakuplayz.cropclick.configurations.ConfigurationKey {

        UPDATE_MESSAGE_PLAYER("updateMessage.player", true),
        UPDATE_MESSAGE_CONSOLE("updateMessage.console", true),
        AUTOFARMS_ENABLED("autofarms.isEnabled", true);

        @NotNull
        private final String path;

        private final Object defaultValue;

    }

}
