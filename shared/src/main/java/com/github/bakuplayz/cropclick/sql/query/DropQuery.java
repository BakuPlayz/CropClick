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

import org.jetbrains.annotations.NotNull;

/**
 * A class representing a typed SQL drop query.
 */
public final class DropQuery extends BaseQuery {

    public DropQuery(@NotNull String table) {
        query.append("DROP TABLE ").append(table);
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as WHERE clauses are not supported for DROP queries.
     */
    @Override
    public DropQuery where(@NotNull String column, @NotNull String operator, @NotNull Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as grouping conditions are not supported for DROP queries.
     */
    @Override
    public DropQuery beginGroup() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as grouping conditions are not supported for DROP queries.
     */
    @Override
    public DropQuery endGroup() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as logical AND conditions are not supported for DROP queries.
     */
    @Override
    public DropQuery and(@NotNull String column, @NotNull String operator, @NotNull Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }


    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, as logical OR conditions are not supported for DROP queries.
     */
    @Override
    public DropQuery or(String column, String operator, Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported operation for DROP.");
    }

}
