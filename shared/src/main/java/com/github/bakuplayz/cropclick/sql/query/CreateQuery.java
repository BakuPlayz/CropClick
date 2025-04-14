/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2025 BakuPlayz
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

import java.util.Map;
import java.util.StringJoiner;

/**
 * A class representing a typed SQL create query, where the type
 * T is the type to insert.
 *
 * @param <T> the type to create according to.
 */
public final class CreateQuery<T> extends BaseQuery {

    public CreateQuery(@NotNull String table, @NotNull Class<T> clazz, boolean ifNotExist) {
        query.append("CREATE TABLE").append(ifNotExist ? " IF NOT EXIST " : " ").append(table);
        insertColumns(clazz);
    }


    /**
     * Inserts the columns and their types provided the clazz type.
     *
     * @param clazz the clazz to find the column-type mappings from.
     */
    private void insertColumns(@NotNull Class<T> clazz) {
        StringJoiner columnDefs = new StringJoiner(", ");
        ColumnMapper<T> mapper = ColumnMapperRegistry.get(clazz);

        for (Map.Entry<String, String> entry : mapper.getColumnsAndTypes().entrySet()) {
            columnDefs.add(entry.getKey() + " " + entry.getValue());
        }

        query.append(" (").append(columnDefs).append(")");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as WHERE clauses are not supported for CREATE queries.
     */
    @Override
    public CreateQuery<T> where(@NotNull String column, @NotNull String operator, @NotNull Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as grouping conditions are not supported for CREATE queries.
     */
    @Override
    public CreateQuery<T> beginGroup() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as grouping conditions are not supported for CREATE queries.
     */
    @Override
    public CreateQuery<T> endGroup() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as logical AND conditions are not supported for CREATE queries.
     */
    @Override
    public CreateQuery<T> and(@NotNull String column, @NotNull String operator, @NotNull Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as logical OR conditions are not supported for CREATE queries.
     */
    @Override
    public CreateQuery<T> or(String column, String operator, Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }

}
