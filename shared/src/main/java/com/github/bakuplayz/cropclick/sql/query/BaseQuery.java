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
package com.github.bakuplayz.cropclick.sql.query;

import com.github.bakuplayz.cropclick.sql.Column;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * A class representing the base for a typed SQL query, where the type
 * T is the type of the result of performing the query.
 *
 * @param <T> the resulting type, after querying.
 */
public abstract class BaseQuery<T> {

    protected final StringBuilder query = new StringBuilder();

    protected final List<Object> parameters = new ArrayList<>();


    protected String build() {
        return query.toString();
    }


    @SuppressWarnings("unchecked")
    public <V> BaseQuery<T> where(@NotNull Column<T, V> column, @NotNull String operator, V value) {
        query.append(" WHERE ").append(column.getName()).append(" ").append(operator).append(" ?");
        parameters.add(value);
        return this;
    }


    protected void appendColumnsAndValues(String keyword, Column<T, ?> @NotNull [] columns) {
        query.append(keyword).append(" (");
        StringJoiner colNames = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        for (Column<T, ?> col : columns) {
            colNames.add(col.getName());
            placeholders.add("?");
        }

        query.append(colNames);
        query.append(") VALUES (").append(placeholders).append(")");
    }

}