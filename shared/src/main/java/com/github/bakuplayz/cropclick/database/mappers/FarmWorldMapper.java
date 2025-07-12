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
package com.github.bakuplayz.cropclick.database.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.bakuplayz.cropclick.common.Maps;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import dev.bakuplayz.spigotstore.persistence.sql.core.DatabaseDialect;
import dev.bakuplayz.spigotstore.persistence.sql.core.LogicalType;
import dev.bakuplayz.spigotstore.registries.entity.api.EntityMapper;
import dev.bakuplayz.spigotstore.registries.json.impl.JsonMapperRegistry;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.AbstractMap.SimpleImmutableEntry;


@AllArgsConstructor
public final class FarmWorldMapper implements EntityMapper<FarmWorld> {

    private final DatabaseDialect dialect;

    private final JsonMapperRegistry jsonRegistry;


    @NotNull
    @Override
    public FarmWorld toEntity(@NotNull ResultSet rs) throws SQLException {
        return FarmWorld.createBasic(
                rs.getString(1),
                rs.getBoolean(2),
                rs.getBoolean(3),
                rs.getBoolean(4),
                jsonRegistry.fromJson(rs.getString(5), new TypeReference<List<String>>() {
                })
        );
    }


    @NotNull
    @Override
    @UnmodifiableView
    public Map<String, String> getColumnDefinitions() {
        return Maps.ofEntries(
                new SimpleImmutableEntry<>("name", dialect.resolveNotNull(LogicalType.STRING, true)),
                new SimpleImmutableEntry<>("is_banished", dialect.resolveNotNull(LogicalType.BOOLEAN)),
                new SimpleImmutableEntry<>("allows_players", dialect.resolveNotNull(LogicalType.BOOLEAN)),
                new SimpleImmutableEntry<>("allows_autofarms", dialect.resolveNotNull(LogicalType.BOOLEAN)),
                new SimpleImmutableEntry<>("banished_addons", dialect.resolveNotNull(LogicalType.JSON))
        );
    }


    @NotNull
    @Override
    public List<Object> getValues(@NotNull FarmWorld entity) {
        return Arrays.asList(
                entity.getName(),
                entity.isBanished(),
                entity.allowsPlayers(),
                entity.allowsAutofarms(),
                entity.getBanishedAddons()
        );
    }

}
