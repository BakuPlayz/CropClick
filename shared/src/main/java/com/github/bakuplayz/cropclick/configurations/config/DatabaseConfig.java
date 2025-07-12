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

import dev.bakuplayz.spigotstore.persistence.sql.core.DatabaseDialect;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYamlKey;
import dev.bakuplayz.spigotstore.persistence.yaml.impl.AbstractPersistentYaml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * A class representing the YAML file: 'database.yml'.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class DatabaseConfig extends AbstractPersistentYaml {

    public DatabaseConfig() {
        super("database.yml");
    }


    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ConfigurationKey implements PersistentYamlKey {

        DEFAULT_USERNAME("databases.default.username", "username"),
        DEFAULT_PASSWORD("databases.default.password", "password"),
        DEFAULT_DATABASE("databases.default.database", "cropclick"),
        DEFAULT_HOST("databases.default.host", "host"),
        DEFAULT_PORT("databases.default.port", 0),
        DEFAULT_DIALECT("databases.default.dialect", DatabaseDialect.MYSQL),

        MIGRATION_USERNAME("databases.migration.username", "username"),
        MIGRATION_PASSWORD("databases.migration.password", "password"),
        MIGRATION_DATABASE("databases.migration.database", "cropclick"),
        MIGRATION_HOST("databases.migration.host", "host"),
        MIGRATION_PORT("databases.migration.port", 0),
        MIGRATION_DIALECT("databases.migration.dialect", DatabaseDialect.MYSQL);

        @NotNull
        private final String path;

        private final Object defaultValue;

    }

}
