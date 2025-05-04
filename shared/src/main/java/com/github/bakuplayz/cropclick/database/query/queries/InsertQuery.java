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
package com.github.bakuplayz.cropclick.database.query.queries;

import com.github.bakuplayz.cropclick.database.EntityMapper;
import com.github.bakuplayz.cropclick.database.EntityMapperRegistry;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing a typed SQL insert query, where the type
 * T is the type to insert.
 *
 * @param <T> the type to update according to.
 */
@AllArgsConstructor
public abstract class InsertQuery<T> extends BaseQuery {

    @NotNull
    private final Class<T> clazz;


    /**
     * Queues the query into the execution queue, and will be executed
     * once a database worker is available.
     *
     * @param scheduler the scheduler to handle the scheduling of this task.
     *
     * @return the future response, true iff successful otherwise false.
     */
    @NotNull
    public CompletableFuture<Boolean> queue(@NotNull QueryScheduler scheduler) {
        return super.baseQueue(scheduler);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public InsertQuery<T> where(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.where(column, operator, value);
        return this;
    }


    public InsertQuery<T> values(@NotNull T instance) {
        StringJoiner columnNames = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        EntityMapper<T> mapper = EntityMapperRegistry.get(clazz);
        List<Object> values = mapper.getValues(instance);
        List<String> columns = mapper.getColumns();

        for (int i = 0; i < columns.size(); i++) {
            columnNames.add(columns.get(i));
            parameters.add(values.get(i));
            placeholders.add("?");
        }

        query.append("(").append(columnNames).append(") VALUES (").append(placeholders).append(")");
        return this;
    }

}
