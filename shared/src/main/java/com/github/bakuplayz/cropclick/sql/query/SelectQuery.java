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

import com.github.bakuplayz.cropclick.sql.QueryScheduler;
import com.github.bakuplayz.cropclick.sql.RowMapper;
import com.github.bakuplayz.cropclick.sql.RowMapperRegistry;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing a typed SQL select query, where the type
 * T is the type of the result of performing the query.
 *
 * @param <T> the resulting type, after querying.
 */
public final class SelectQuery<T> extends BaseQuery {

    private final Class<T> clazz;


    /**
     * Constructor for selecting all, i.e. using the '*' operator, from
     * the provided table.
     *
     * @param table the table to select from.
     */
    public SelectQuery(@NotNull String table, @NotNull Class<T> clazz) {
        this.clazz = clazz;
        query.append("SELECT * FROM ").append(table);
    }


    @Override
    public CompletableFuture<Boolean> execute(@NotNull QueryScheduler scheduler) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }


    @Override
    public SelectQuery<T> where(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.where(column, operator, value);
        return this;
    }


    @Override
    public SelectQuery<T> beginGroup() {
        super.beginGroup();
        return this;
    }


    @Override
    public SelectQuery<T> endGroup() {
        super.endGroup();
        return this;
    }


    @Override
    public SelectQuery<T> and(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.and(column, operator, value);
        return this;
    }


    @Override
    public SelectQuery<T> or(String column, String operator, Object value) {
        super.or(column, operator, value);
        return this;
    }


    public SelectQuery<T> from(@NotNull String table) {
        query.append(" FROM ").append(table);
        return this;
    }


    public SelectQuery<T> limit(int start, int count) {
        query.append(" LIMIT ?, ?");
        parameters.add(start);
        parameters.add(start + count);
        return this;
    }


    public SelectQuery<T> limit(int count) {
        query.append(" LIMIT ?");
        parameters.add(count);
        return this;
    }


    @NotNull
    public CompletableFuture<T> fetchOne(@NotNull QueryScheduler scheduler) {
        CompletableFuture<T> completable = new CompletableFuture<>();
        RowMapper<T> mapper = RowMapperRegistry.get(clazz);

        scheduler.queue((connection -> {
            try (PreparedStatement statement = connection.prepareStatement(build())) {
                for (int i = 0; i < parameters.size(); ++i) {
                    statement.setObject(i + 1, parameters.get(i));
                }

                try (ResultSet rs = statement.executeQuery()) {
                    completable.complete(rs.next() ? mapper.map(rs) : null);
                }
            } catch (SQLException e) {
                logDebug("Could not perform SQL fetchOne query, something went wrong.", e);
            }
        }));

        return completable;
    }


    @NotNull
    public CompletableFuture<List<T>> fetchAll(@NotNull QueryScheduler scheduler) {
        CompletableFuture<List<T>> completable = new CompletableFuture<>();
        RowMapper<T> mapper = RowMapperRegistry.get(clazz);

        scheduler.queue((connection -> {
            try (PreparedStatement statement = connection.prepareStatement(build())) {
                for (int i = 0; i < parameters.size(); ++i) {
                    statement.setObject(i + 1, parameters.get(i));
                }

                try (ResultSet rs = statement.executeQuery()) {
                    List<T> results = new ArrayList<>();
                    while (rs.next()) {
                        results.add(mapper.map(rs));
                    }
                    completable.complete(results);
                }
            } catch (SQLException e) {
                logDebug("Could not perform SQL fetchAll query, something went wrong.", e);
            }
        }));

        return completable;
    }

}
