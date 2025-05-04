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

import com.github.bakuplayz.cropclick.database.QueryScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

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
        return where("1", "=", "1");
    }


    /**
     * Queues the query into the execution queue, and will be executed
     * once a database worker is available.
     *
     * @param scheduler the scheduler to handle the scheduling of this task.
     *
     * @return the future response, true iff successful otherwise false.
     */
    public CompletableFuture<Boolean> queue(@NotNull QueryScheduler scheduler) {
        return super.baseQueue(scheduler);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteQuery where(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.where(column, operator, column);
        return this;
    }

}
