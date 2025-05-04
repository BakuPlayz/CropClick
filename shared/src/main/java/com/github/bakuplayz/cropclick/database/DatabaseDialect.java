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

import com.github.bakuplayz.cropclick.common.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum DatabaseDialect {

    MYSQL("mysql", Maps.of(
            LogicalType.STRING, "VARCHAR(255)",
            LogicalType.TEXT, "TEXT",
            LogicalType.INT, "INT",
            LogicalType.FLOAT, "FLOAT",
            LogicalType.BOOLEAN, "BOOLEAN",
            LogicalType.UUID, "BINARY(16)",
            LogicalType.JSON, "JSON",
            LogicalType.TIMESTAMP, "DATETIME",
            LogicalType.DATE, "DATE",
            LogicalType.BINARY, "BLOB"
    )),
    MARIADB("mariadb", Maps.of(
            LogicalType.STRING, "VARCHAR(255)",
            LogicalType.TEXT, "TEXT",
            LogicalType.INT, "INT",
            LogicalType.FLOAT, "FLOAT",
            LogicalType.BOOLEAN, "BOOLEAN",
            LogicalType.UUID, "BINARY(16)",
            LogicalType.JSON, "JSON",
            LogicalType.TIMESTAMP, "DATETIME",
            LogicalType.DATE, "DATE",
            LogicalType.BINARY, "BLOB"
    )),
    POSTGRES("postgres", Maps.of(
            LogicalType.STRING, "VARCHAR",
            LogicalType.TEXT, "VARCHAR",
            LogicalType.INT, "INTEGER",
            LogicalType.JSON, "JSONB",
            LogicalType.FLOAT, "REAL",
            LogicalType.BOOLEAN, "BOOLEAN",
            LogicalType.UUID, "BYTEA",
            LogicalType.JSON, "JSONB",
            LogicalType.TIMESTAMP, "TIMESTAMP WITH TIME ZONE",
            LogicalType.DATE, "DATE",
            LogicalType.BINARY, "BYTEA"
    ));

    private final String name;

    private final Map<LogicalType, String> typeMappings;


    @NotNull
    public String resolve(@NotNull LogicalType logicalType) {
        return resolve(logicalType, false);
    }


    @NotNull
    public String resolve(@NotNull LogicalType logicalType, boolean primaryKey) {
        return typeMappings.getOrDefault(logicalType, logicalType.name().toLowerCase()) + (primaryKey ? " PRIMARY KEY" : "");
    }


    @NotNull
    public String resolveNotNull(@NotNull LogicalType logicalType) {
        return resolveNotNull(logicalType, false);
    }


    @NotNull
    public String resolveNotNull(@NotNull LogicalType logicalType, boolean primaryKey) {
        return primaryKey ? resolve(logicalType) + " PRIMARY KEY NOT NULL" : resolve(logicalType) + " NOT NULL";
    }

}