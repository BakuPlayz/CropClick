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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmFactory;
import com.github.bakuplayz.cropclick.common.Maps;
import com.github.bakuplayz.cropclick.database.DatabaseDialect;
import com.github.bakuplayz.cropclick.database.EntityMapper;
import com.github.bakuplayz.cropclick.database.LogicalType;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.AbstractMap.SimpleImmutableEntry;

@AllArgsConstructor
public final class AutofarmMapper implements EntityMapper<Autofarm> {

    private final ObjectMapper mapper;

    private final DatabaseDialect dialect;


    @NotNull
    @Override
    public Autofarm toEntity(@NotNull ResultSet rs) throws SQLException, IOException {
        return AutofarmFactory.createPlain(
                UUID.nameUUIDFromBytes(rs.getBytes(1)),
                UUID.nameUUIDFromBytes(rs.getBytes(2)),
                rs.getBoolean(3),
                mapper.readValue(rs.getString(4), Location.class),
                mapper.readValue(rs.getString(5), Location.class),
                mapper.readValue(rs.getString(6), Location.class)
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
                entity.getCropLocation(),
                entity.getContainerLocation(),
                entity.getDispenserLocation()
        );
    }

}
