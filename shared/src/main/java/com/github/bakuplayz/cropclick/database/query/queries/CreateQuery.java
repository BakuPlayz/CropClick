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
package com.github.bakuplayz.cropclick.database.query.queries;

import com.github.bakuplayz.cropclick.database.EntityMapper;
import com.github.bakuplayz.cropclick.database.EntityMapperRegistry;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing a typed SQL create query,
 * where the type T is the type to create.
 *
 * @param <T> the type to create according to.
 */
public final class CreateQuery<T> extends BaseQuery {


    public CreateQuery(@NotNull String table, @NotNull Class<T> clazz, boolean ifNotExist) {
        query.append("CREATE TABLE").append(ifNotExist ? " IF NOT EXISTS " : " ").append(table);
        insertColumns(clazz);
    }


    /**
     * Inserts the columns and their types provided the clazz type.
     *
     * @param clazz the clazz to find the column-type mappings from.
     */
    private void insertColumns(@NotNull Class<T> clazz) {
        StringJoiner columnDefs = new StringJoiner(", ");
        EntityMapper<T> mapper = EntityMapperRegistry.get(clazz);

        for (Map.Entry<String, String> entry : mapper.getColumnDefinitions().entrySet()) {
            columnDefs.add(entry.getKey() + " " + entry.getValue());
        }

        query.append(" (").append(columnDefs).append(")");
    }


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

}
