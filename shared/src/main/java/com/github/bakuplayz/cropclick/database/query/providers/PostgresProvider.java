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
package com.github.bakuplayz.cropclick.database.query.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bakuplayz.cropclick.database.query.QueryProvider;
import com.github.bakuplayz.cropclick.database.query.postgres.InsertQuery;
import com.github.bakuplayz.cropclick.database.query.queries.*;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class PostgresProvider implements QueryProvider {

    private final ObjectMapper jsonMapper;


    @NotNull
    @Override
    public <T> CreateQuery<T> create(@NotNull String table, @NotNull Class<T> clazz, boolean ifNotExist) {
        return new CreateQuery<>(table, clazz, ifNotExist);
    }


    @NotNull
    @Override
    public <T> InsertQuery<T> insert(@NotNull String table, @NotNull Class<T> clazz, boolean ifNotExist) {
        return new com.github.bakuplayz.cropclick.database.query.postgres.InsertQuery<>(table, clazz, ifNotExist);
    }


    @NotNull
    @Override
    public <T> UpdateQuery<T> update(@NotNull String table, @NotNull Class<T> clazz) {
        return new UpdateQuery<>(table, clazz);
    }


    @NotNull
    @Override
    public CountQuery count(@NotNull String table) {
        return new CountQuery(table);
    }


    @NotNull
    @Override
    public DeleteQuery delete(@NotNull String table) {
        return new DeleteQuery(table);
    }


    @NotNull
    @Override
    public <T> SelectQuery<T> select(@NotNull String table, @NotNull Class<T> clazz) {
        return new com.github.bakuplayz.cropclick.database.query.postgres.SelectQuery<>(jsonMapper, table, clazz);
    }

}
