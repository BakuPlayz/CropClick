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

import com.github.bakuplayz.cropclick.datacontainers.migration.MigrationStatus;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYamlKey;
import dev.bakuplayz.spigotstore.persistence.yaml.impl.AbstractPersistentYaml;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public final class UsageConfig extends AbstractPersistentYaml {

    public UsageConfig() {
        super("usage.yml");

        clearMigrationStates();
    }


    private void clearMigrationStates() {
        setWithoutSave(ConfigurationKey.DATABASES_MIGRATION_TIMESTAMP, 0);
        setWithoutSave(ConfigurationKey.DATABASES_MIGRATION_STATUS, MigrationStatus.NOT_INITIATED);
        save();
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ConfigurationKey implements PersistentYamlKey {

        DATABASES_DEFAULT_CONNECTED("databases.default.connected", false),
        
        DATABASES_MIGRATION_CONNECTED("databases.migration.connected", false),
        DATABASES_MIGRATION_STATUS("databases.migration.status", MigrationStatus.NOT_INITIATED),
        DATABASES_MIGRATION_TIMESTAMP("databases.migration.timestamp", 0L);

        @NotNull
        private final String path;

        private final Object defaultValue;

    }

}
