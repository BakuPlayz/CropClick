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

import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.sql.Column;
import com.github.bakuplayz.cropclick.sql.QueryScheduler;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing the base for a typed SQL query.
 */
public abstract class BaseQuery implements LoggerContext {

    protected final StringBuilder query = new StringBuilder();

    protected final List<Object> parameters = new ArrayList<>();


    protected String build() {
        return query.toString();
    }


    public CompletableFuture<Boolean> execute(@NotNull QueryScheduler scheduler) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        scheduler.queue((connection) -> {
            try (PreparedStatement statement = connection.prepareStatement(build())) {
                for (int i = 0; i < parameters.size(); ++i) {
                    statement.setObject(i + 1, parameters.get(i));
                }

                statement.executeUpdate();
                future.complete(true);
            } catch (SQLException e) {
                logDebug("Could not perform SQL update query, something went wrong.", e);
                future.complete(false);
            }
        });

        return future;
    }


    public BaseQuery where(@NotNull Column<?> column, @NotNull String operator, Object value) {
        query.append(" WHERE ").append(column.getName()).append(" ").append(operator).append(" ?");
        parameters.add(value);
        return this;
    }


}