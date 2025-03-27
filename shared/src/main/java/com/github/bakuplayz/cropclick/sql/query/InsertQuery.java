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

import java.util.Arrays;

/**
 * A class representing a typed SQL insert query, where the type
 * T is the type of the result of performing the query.
 *
 * @param <T> the resulting type, after querying.
 */
public class InsertQuery<T> extends BaseQuery<T> {

    @SafeVarargs
    public InsertQuery(@NotNull String table, Column<T, ?>... columns) {
        query.append("INSERT INTO ").append(table);
        appendColumnsAndValues("", columns);
    }


    public InsertQuery<T> values(Object... values) {
        parameters.addAll(Arrays.asList(values));
        return this;
    }

}
