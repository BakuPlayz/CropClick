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

import java.util.Collection;
import java.util.StringJoiner;

/**
 * A class representing a typed SQL insert query.
 */
public final class InsertQuery<T> extends BaseQuery {

    private final Column<T>[] columns;


    public InsertQuery(@NotNull String table, Collection<? extends Column<?>> columns) {
        query.append("INSERT INTO ").append(table);
        appendColumnsAndValues(columns.toArray(Column[]::new));
    }


    public InsertQuery(@NotNull String table, Column<?>... columns) {
        query.append("INSERT INTO ").append(table);
        appendColumnsAndValues(columns);
    }


    private void appendColumnsAndValues(Column<?> @NotNull [] columns) {
        query.append(" (");
        StringJoiner colNames = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        for (Column<?> col : columns) {
            colNames.add(col.getName());
            placeholders.add("?");
        }

        query.append(colNames);
        query.append(") VALUES (").append(placeholders).append(")");
    }


    public InsertQuery<T> values(@NotNull T instance) {
        // TODO: ColumnMapper.get(Autofarm.class).forEach(value -> parameters.add(value));
        for (Column<T> column : columns) {
            parameters.add(column.getGetter().apply(instance));
        }
        return this;
    }


    @Override
    public InsertQuery where(@NotNull Column<?, ?> column, @NotNull String operator, Object value) {
        super.where(column, operator, column.getType().cast(value));
        return this;
    }

}
