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
package com.github.bakuplayz.cropclick.database.query.mysql;

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
            if (operator.equals("=")) {
                query.append("JSON_CONTAINS(JSON_EXTRACT(data, '$.").append(column).append("'), ?)");
            } else {
                query.append("JSON_UNQUOTE(JSON_EXTRACT(data, '$.").append(column).append("')) ")
                        .append(operator).append(" ?");
            }
        } else {
            if (operator.equals("=")) {
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) query.append(" OR ");
                    query.append("JSON_CONTAINS(JSON_EXTRACT(data, '$.")
                            .append(column).append("'), ?)");
                }
            } else if (operator.equals("!=")) {
                query.append("JSON_UNQUOTE(JSON_EXTRACT(data, '$.")
                        .append(column).append("')) NOT IN (")
                        .append(repeatPlaceholders(values.length)).append(")");
            } else {
                query.append("JSON_UNQUOTE(JSON_EXTRACT(data, '$.")
                        .append(column).append("')) IN (")
                        .append(repeatPlaceholders(values.length)).append(")");
            }
        }

        return this;
    }

}
