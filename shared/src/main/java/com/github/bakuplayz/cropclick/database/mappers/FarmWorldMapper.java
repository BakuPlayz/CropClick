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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.common.Maps;
import com.github.bakuplayz.cropclick.database.EntityMapper;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.AbstractMap.SimpleImmutableEntry;


@AllArgsConstructor
public final class FarmWorldMapper implements EntityMapper<FarmWorld> {

    private final ObjectMapper mapper;


    @NotNull
    @Override
    public FarmWorld toEntity(@NotNull ResultSet rs) throws SQLException, IOException {
        return new FarmWorld(
                rs.getString(1),
                rs.getBoolean(2),
                rs.getBoolean(3),
                rs.getBoolean(4),
                mapper.readValue(rs.getString(5), new TypeReference<List<AbstractAddon>>() {
                })
        );
    }


    @NotNull
    @Override
    @UnmodifiableView
    public Map<String, String> getColumnDefinitions() {
        return Maps.ofEntries(
                new SimpleImmutableEntry<>("name", "TEXT PRIMARY KEY NOT NULL"),
                new SimpleImmutableEntry<>("is_banished", "BOOLEAN NOT NULL"),
                new SimpleImmutableEntry<>("allows_players", "BOOLEAN NOT NULL"),
                new SimpleImmutableEntry<>("allows_autofarms", "BOOLEAN NOT NULL"),
                new SimpleImmutableEntry<>("banished_addons", "JSON NOT NULL")
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
