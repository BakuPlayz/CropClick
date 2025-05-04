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
import com.github.bakuplayz.cropclick.database.DatabaseDialect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * A class representing the YAML file: 'database.yml'.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class DatabaseConfig extends AbstractConfiguration {

    // TODO: Create this file... in resources...


    public DatabaseConfig(@NotNull CropClick plugin) {
        super(plugin, "database.yml");
    }


    @Getter
    @AllArgsConstructor
    public enum ConfigurationKey implements com.github.bakuplayz.cropclick.configurations.ConfigurationKey {

        PASSWORD("password", "password"),
        USERNAME("username", "username"),
        DATABASE("database", "database"),
        HOST("host", "host"),
        PORT("port", "port"),
        DIALECT("dialect", DatabaseDialect.MYSQL);

        @NotNull
        private final String path;

        private final Object defaultValue;

    }

}
