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

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.Maps;
import dev.bakuplayz.spigotstore.persistence.sql.core.DatabaseDialect;
import dev.bakuplayz.spigotstore.persistence.sql.core.LogicalType;
import dev.bakuplayz.spigotstore.registries.entity.api.EntityMapper;
import dev.bakuplayz.spigotstore.registries.json.impl.JsonMapperRegistry;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.AbstractMap.SimpleImmutableEntry;

@AllArgsConstructor
public final class AutofarmMapper implements EntityMapper<Autofarm> {

    private final DatabaseDialect dialect;

    private final JsonMapperRegistry jsonRegistry;


    @NotNull
    @Override
    public Autofarm toEntity(@NotNull ResultSet rs) throws SQLException {
        return Autofarm.createBasic(
                readUUID(rs, 1, dialect),
                readUUID(rs, 2, dialect),
                rs.getBoolean(3),
                jsonRegistry.fromJson(rs.getString(4), Location.class),
                jsonRegistry.fromJson(rs.getString(5), Location.class),
                jsonRegistry.fromJson(rs.getString(6), Location.class)
        );
    }


    @NotNull
    @Override
    @UnmodifiableView
    public Map<String, String> getColumnDefinitions() {
        return Maps.ofEntries(
                new SimpleImmutableEntry<>("farmer", dialect.resolveNotNull(LogicalType.UUID, true)),
                new SimpleImmutableEntry<>("owner", dialect.resolveNotNull(LogicalType.UUID)),
                new SimpleImmutableEntry<>("is_enabled", dialect.resolveNotNull(LogicalType.BOOLEAN)),
                new SimpleImmutableEntry<>("crop", dialect.resolveNotNull(LogicalType.JSON)),
                new SimpleImmutableEntry<>("container", dialect.resolveNotNull(LogicalType.JSON)),
                new SimpleImmutableEntry<>("dispenser", dialect.resolveNotNull(LogicalType.JSON))
        );
    }


    @NotNull
    @Override
    public List<Object> getValues(@NotNull Autofarm entity) {
        return Arrays.asList(
                entity.getFarmerId(),
                entity.getOwnerId(),
                entity.isEnabled(),
                jsonRegistry.toJsonString(entity.getCropLocation()),
                jsonRegistry.toJsonString(entity.getContainerLocation()),
                jsonRegistry.toJsonString(entity.getDispenserLocation())
        );
    }

}
