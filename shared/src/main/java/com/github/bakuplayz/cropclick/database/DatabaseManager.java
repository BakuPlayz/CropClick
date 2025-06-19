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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig;
import com.github.bakuplayz.cropclick.database.mappers.AutofarmMapper;
import com.github.bakuplayz.cropclick.database.mappers.FarmWorldMapper;
import com.github.bakuplayz.cropclick.database.serializers.*;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import dev.bakuplayz.spigotstore.database.ConnectionPool;
import dev.bakuplayz.spigotstore.database.DatabaseDialect;
import dev.bakuplayz.spigotstore.database.DatabaseOptions;
import dev.bakuplayz.spigotstore.database.QueryScheduler;
import dev.bakuplayz.spigotstore.database.entity.EntityMapperRegistry;
import dev.bakuplayz.spigotstore.database.query.providers.MySQLProvider;
import dev.bakuplayz.spigotstore.database.query.providers.PostgresProvider;
import dev.bakuplayz.spigotstore.database.query.providers.QueryProvider;
import dev.bakuplayz.spigotstore.database.query.providers.SQLiteProvider;
import lombok.Getter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig.ConfigurationKey;

@Getter
public final class DatabaseManager {

    private final ObjectMapper jsonMapper;

    private final QueryProvider queryProvider;

    private final QueryScheduler queryScheduler;

    private final ConnectionPool connectionPool;


    public DatabaseManager(@NotNull CropClick plugin) {
        DatabaseConfig config = plugin.getConfigManager().getDatabaseConfig();

        DatabaseOptions options = new DatabaseOptions(
                config.getInt(ConfigurationKey.PORT),
                config.getString(ConfigurationKey.HOST),
                config.getString(ConfigurationKey.USERNAME),
                config.getString(ConfigurationKey.PASSWORD),
                config.getString(ConfigurationKey.DATABASE),
                config.getEnum(ConfigurationKey.DIALECT, DatabaseDialect.class)
        );

        this.jsonMapper = initializeJSONMapper();
        this.queryProvider = initializeProvider(config);
        this.connectionPool = new ConnectionPool(options);
        this.queryScheduler = new QueryScheduler(connectionPool, plugin.getTaskScheduler());

        registerEntities(config);
    }


    @NotNull
    private QueryProvider initializeProvider(@NotNull DatabaseConfig config) {
        DatabaseDialect dialect = config.getEnum(ConfigurationKey.DIALECT, DatabaseDialect.class);

        switch (dialect) {
            case MARIADB:
            case MYSQL:
                return new MySQLProvider(jsonMapper);
            case POSTGRES:
                return new PostgresProvider(jsonMapper);
            default:
                return new SQLiteProvider(jsonMapper);
        }
    }


    @NotNull
    private ObjectMapper initializeJSONMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(FarmWorld.class, new FarmWorldSerializer());
        module.addDeserializer(FarmWorld.class, new FarmWorldDeserializer());
        module.addSerializer(Location.class, new LocationSerializer());
        module.addDeserializer(Location.class, new LocationDeserializer());
        module.addSerializer(Autofarm.class, new AutofarmSerializer());
        module.addDeserializer(Autofarm.class, new AutofarmDeserializer());
        mapper.registerModule(module);

        return mapper;
    }


    private void registerEntities(@NotNull DatabaseConfig config) {
        DatabaseDialect dialect = config.getEnum(ConfigurationKey.DIALECT, DatabaseDialect.class);
        EntityMapperRegistry.register(Autofarm.class, new AutofarmMapper(jsonMapper, dialect));
        EntityMapperRegistry.register(FarmWorld.class, new FarmWorldMapper(jsonMapper, dialect));
    }


}
