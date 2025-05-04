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

import org.jetbrains.annotations.NotNull;

public final class InsertQuery<T> extends com.github.bakuplayz.cropclick.database.query.queries.InsertQuery<T> {

    public InsertQuery(@NotNull String table, @NotNull Class<T> clazz, boolean ifNotExist) {
        super(clazz);
        query.append("INSERT").append(ifNotExist ? " IGNORE " : " ").append("INTO ").append(table);
    }

}
