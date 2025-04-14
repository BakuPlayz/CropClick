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

import com.github.bakuplayz.cropclick.sql.ColumnMapper;
import com.github.bakuplayz.cropclick.sql.ColumnMapperRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

/**
 * A class representing a typed SQL insert query, where the type
 * T is the type to insert.
 *
 * @param <T> the type to update according to.
 */
public final class InsertQuery<T> extends BaseQuery {

    private final Class<T> clazz;


    public InsertQuery(@NotNull String table, @NotNull Class<T> clazz) {
        this.clazz = clazz;
        query.append("INSERT INTO ").append(table);
    }


    @Override
    public InsertQuery<T> where(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.where(column, operator, value);
        return this;
    }


    @Override
    public InsertQuery<T> beginGroup() {
        super.beginGroup();
        return this;
    }


    @Override
    public InsertQuery<T> endGroup() {
        super.endGroup();
        return this;
    }


    @Override
    public InsertQuery<T> and(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.and(column, operator, value);
        return this;
    }


    @Override
    public InsertQuery<T> or(String column, String operator, Object value) {
        super.or(column, operator, value);
        return this;
    }


    public InsertQuery<T> values(@NotNull T instance) {
        StringJoiner columnNames = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        ColumnMapper<T> mapper = ColumnMapperRegistry.get(clazz);
        List<String> columns = mapper.getColumns();
        List<Object> values = mapper.getValues(instance);

        for (int i = 0; i < columns.size(); i++) {
            columnNames.add(columns.get(i));
            parameters.add(values.get(i));
            placeholders.add("?");
        }

        query.append("(").append(columnNames).append(") VALUES (").append(placeholders).append(")");
        return this;
    }

}
