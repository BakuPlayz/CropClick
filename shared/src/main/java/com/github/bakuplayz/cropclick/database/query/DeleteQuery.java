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
package com.github.bakuplayz.cropclick.database.query;

import org.jetbrains.annotations.NotNull;

/**
 * A class representing a typed SQL delete query.
 */
public final class DeleteQuery extends BaseQuery {

    public DeleteQuery(@NotNull String table) {
        query.append("DELETE FROM ").append(table);
    }


    /**
     * Adds a WHERE clause with a default condition {@code 1=1}, which matches all records.
     *
     * @return the query instance.
     */
    public DeleteQuery matchAll() {
        return where("1", "=", 1);
    }


    @Override
    public DeleteQuery where(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.where(column, operator, column);
        return this;
    }


    @Override
    public DeleteQuery beginGroup() {
        super.beginGroup();
        return this;
    }


    @Override
    public DeleteQuery endGroup() {
        super.endGroup();
        return this;
    }


    @Override
    public DeleteQuery and(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.and(column, operator, value);
        return this;
    }


    @Override
    public DeleteQuery or(String column, String operator, Object value) {
        super.or(column, operator, value);
        return this;
    }

}
