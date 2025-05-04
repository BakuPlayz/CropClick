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

import com.github.bakuplayz.cropclick.database.query.queries.*;
import org.jetbrains.annotations.NotNull;

public interface QueryProvider {

    <T> CreateQuery<T> create(@NotNull String table, @NotNull Class<T> clazz, boolean ifNotExist);


    <T> InsertQuery<T> insert(@NotNull String table, @NotNull Class<T> clazz, boolean ifNotExist);


    <T> UpdateQuery<T> update(@NotNull String table, @NotNull Class<T> clazz);


    CountQuery count(@NotNull String table);


    DeleteQuery delete(@NotNull String table);


    <T> SelectQuery<T> select(@NotNull String table, @NotNull Class<T> clazz);

}
