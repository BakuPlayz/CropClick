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
package com.github.bakuplayz.cropclick.database.query.postgres;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

public final class SelectQuery<T> extends com.github.bakuplayz.cropclick.database.query.queries.SelectQuery<T> {

    private final ObjectMapper jsonMapper;


    public SelectQuery(@NotNull ObjectMapper jsonMapper, @NotNull String table, @NotNull Class<T> clazz) {
        super(table, clazz);
        this.jsonMapper = jsonMapper;
    }


    @Override
    public SelectQuery<T> whereJSON(@NotNull String column, @NotNull String operator, Object @NotNull ... values) {
        values(jsonMapper, values);

        query.append(" WHERE ");

        if (values.length == 1) {
            query.append("(data->'").append(column).append("')::text ")
                    .append(operator).append(" ?");
        } else {
            query.append("(data->'").append(column).append("')::text ");
            if (operator.equals("=")) {
                query.append("IN (")
                        .append(repeatPlaceholders(values.length)).append(")");
            } else if (operator.equals("!=")) {
                query.append("NOT IN (")
                        .append(repeatPlaceholders(values.length)).append(")");
            } else {
                throw new UnsupportedOperationException("Operator not supported for multiple values in Postgres: " + operator);
            }
        }

        return this;
    }

}
