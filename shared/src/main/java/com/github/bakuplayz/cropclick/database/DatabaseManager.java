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
import com.github.bakuplayz.cropclick.database.query.QueryProvider;
import com.github.bakuplayz.cropclick.database.query.providers.MySQLProvider;
import com.github.bakuplayz.cropclick.database.query.providers.PostgresProvider;
import com.github.bakuplayz.cropclick.database.serializers.LocationDeserializer;
import com.github.bakuplayz.cropclick.database.serializers.LocationSerializer;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
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

        this.jsonMapper = initializeJSONMapper();
        this.queryProvider = initializeProvider(config);
        this.connectionPool = new ConnectionPool(config);
        this.queryScheduler = new QueryScheduler(connectionPool, plugin.getTaskScheduler());

        registerEntities(config);
    }


    @NotNull
    private QueryProvider initializeProvider(@NotNull DatabaseConfig config) {
        DatabaseDialect dialect = config.get(ConfigurationKey.DIALECT);

        switch (dialect) {
            case MARIADB:
            case MYSQL:
                return new MySQLProvider(jsonMapper);
            case POSTGRES:
                return new PostgresProvider(jsonMapper);
            default:
                throw new RuntimeException(String.format("Cannot find provider for the %s dialect, no implementation exist.", dialect.getName()));
        }
    }


    @NotNull
    private ObjectMapper initializeJSONMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(Location.class, new LocationSerializer());
        module.addDeserializer(Location.class, new LocationDeserializer());
        mapper.registerModule(module);

        return mapper;
    }


    private void registerEntities(@NotNull DatabaseConfig config) {
        DatabaseDialect dialect = config.get(ConfigurationKey.DIALECT);
        EntityMapperRegistry.register(Autofarm.class, new AutofarmMapper(jsonMapper, dialect));
        EntityMapperRegistry.register(FarmWorld.class, new FarmWorldMapper(jsonMapper, dialect));
    }

}
