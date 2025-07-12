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
package com.github.bakuplayz.cropclick.database;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig;
import com.github.bakuplayz.cropclick.database.mappers.AutofarmMapper;
import com.github.bakuplayz.cropclick.database.mappers.FarmWorldMapper;
import com.github.bakuplayz.cropclick.database.serializers.*;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import dev.bakuplayz.spigotstore.SpigotStore;
import dev.bakuplayz.spigotstore.persistence.sql.api.QueryProvider;
import dev.bakuplayz.spigotstore.persistence.sql.core.DatabaseDialect;
import dev.bakuplayz.spigotstore.persistence.sql.core.DatabaseOptions;
import dev.bakuplayz.spigotstore.registries.entity.impl.EntityMapperRegistry;
import dev.bakuplayz.spigotstore.registries.json.impl.JsonMapperRegistry;
import lombok.Getter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig.ConfigurationKey;

@Getter
public final class DatabaseManager {

    private final LiveDatabaseContext liveContext;

    private final MigrationDatabaseContext migrationContext;

    private final JsonMapperRegistry jsonRegistry;


    public DatabaseManager(@NotNull CropClick plugin) {
        this.liveContext = new LiveDatabaseContext(plugin);
        this.migrationContext = new MigrationDatabaseContext(plugin);
        this.jsonRegistry = plugin.getStore().getJsonRegistry();

        registerJsonMappers();
    }


    // TODO: Move to SpigotStore -> Log.severe("Failed to close down database connections, memory might leak. Report to author!");


    private void registerJsonMappers() {
        jsonRegistry.register(FarmWorld.class, new FarmWorldSerializer(), new FarmWorldDeserializer());
        jsonRegistry.register(Location.class, new LocationSerializer(), new LocationDeserializer());
        jsonRegistry.register(Autofarm.class, new AutofarmSerializer(), new AutofarmDeserializer());
    }


    public static final class LiveDatabaseContext extends DatabaseContext {

        public LiveDatabaseContext(@NotNull CropClick plugin) {
            super(plugin);
            start();
        }


        @NotNull
        @Override
        protected DatabaseOptions getOptions() {
            return new DatabaseOptions(
                    config.getInt(ConfigurationKey.DEFAULT_PORT),
                    config.getString(ConfigurationKey.DEFAULT_HOST),
                    config.getString(ConfigurationKey.DEFAULT_USERNAME),
                    config.getString(ConfigurationKey.DEFAULT_PASSWORD),
                    config.getString(ConfigurationKey.DEFAULT_DATABASE),
                    config.getEnum(ConfigurationKey.DEFAULT_DIALECT, DatabaseDialect.class)
            );
        }


        @NotNull
        @Override
        protected DatabaseDialect getDialect() {
            return config.getEnum(ConfigurationKey.DEFAULT_DIALECT, DatabaseDialect.class);
        }

    }

    public static final class MigrationDatabaseContext extends DatabaseContext {

        public MigrationDatabaseContext(@NotNull CropClick plugin) {
            super(plugin);
        }


        public void start() {
            super.start();
        }


        @NotNull
        @Override
        protected DatabaseOptions getOptions() {
            return new DatabaseOptions(
                    config.getInt(ConfigurationKey.MIGRATION_PORT),
                    config.getString(ConfigurationKey.MIGRATION_HOST),
                    config.getString(ConfigurationKey.MIGRATION_USERNAME),
                    config.getString(ConfigurationKey.MIGRATION_PASSWORD),
                    config.getString(ConfigurationKey.MIGRATION_DATABASE),
                    config.getEnum(ConfigurationKey.MIGRATION_DIALECT, DatabaseDialect.class)
            );
        }


        @NotNull
        @Override
        protected DatabaseDialect getDialect() {
            return config.getEnum(ConfigurationKey.MIGRATION_DIALECT, DatabaseDialect.class);
        }

    }

    @Getter
    public abstract static class DatabaseContext {


        @NotNull
        protected final DatabaseConfig config;

        protected final SpigotStore store;

        private QueryProvider queryProvider;


        public DatabaseContext(@NotNull CropClick plugin) {
            this.config = plugin.getConfigManager().getDatabaseConfig();
            this.store = plugin.getStore();

            registerEntities();
        }


        protected void start() {
            if (queryProvider != null) return;
            this.queryProvider = store.getProviderSelector().select(getOptions());
        }


        protected abstract DatabaseOptions getOptions();


        protected abstract DatabaseDialect getDialect();


        private void registerEntities() {
            DatabaseDialect dialect = getDialect();
            JsonMapperRegistry jsonRegistry = store.getJsonRegistry();
            EntityMapperRegistry entityRegistry = store.getEntityRegistry();
            entityRegistry.register(dialect, Autofarm.class, new AutofarmMapper(dialect, jsonRegistry));
            entityRegistry.register(dialect, FarmWorld.class, new FarmWorldMapper(dialect, jsonRegistry));
        }

    }

}
