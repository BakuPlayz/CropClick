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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.database.EntityMapper;
import com.github.bakuplayz.cropclick.database.EntityMapperRegistry;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing a typed SQL select query, where the type
 * T is the type of the result of performing the query.
 *
 * @param <T> the resulting type, after querying.
 */
public abstract class SelectQuery<T> extends BaseQuery {

    private final Class<T> clazz;


    /**
     * Constructor for selecting all, i.e. using the '*' operator, from
     * the provided table.
     *
     * @param table the table to select from.
     */
    public SelectQuery(@NotNull String table, @NotNull Class<T> clazz) {
        query.append("SELECT * FROM ").append(table);
        this.clazz = clazz;
    }


    protected SelectQuery<T> values(@NotNull ObjectMapper jsonMapper, Object @NotNull ... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one value must be provided for whereJSON.");
        }

        List<String> strValues = new ArrayList<>();

        for (Object value : values) {
            try {
                strValues.add(jsonMapper.writeValueAsString(value));
            } catch (JsonProcessingException e) {
                Log.debug("Failed to convert object instance to string, reverting query forward.", e);
                return this;
            }
        }

        parameters.addAll(strValues);

        return this;
    }


    @NotNull
    protected String repeatPlaceholders(int count) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < count; ++i) {
            joiner.add("?");
        }
        return joiner.toString();
    }


    /**
     * Adds a WHERE clause that compares one or more values against a field within a JSON column,
     * adapting the query syntax depending on the underlying database protocol.
     *
     * @param column   the JSON key to extract from the data column.
     * @param operator the comparison operator, e.g. "=" or "!=".
     * @param values   the JSON-formatted value(s) to compare against.
     *
     * @return the query instance.
     */
    public abstract SelectQuery<T> whereJSON(@NotNull String column, @NotNull String operator, Object @NotNull ... values);


    /**
     * {@inheritDoc}
     */
    @Override
    public SelectQuery<T> where(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.where(column, operator, value);
        return this;
    }


    public SelectQuery<T> limit(int count) {
        query.append(" LIMIT ?");
        parameters.add(count);
        return this;
    }


    public SelectQuery<T> limit(int start, int count) {
        query.append(" LIMIT ?, ?");
        parameters.add(start);
        parameters.add(start + count);
        return this;
    }


    @NotNull
    public CompletableFuture<T> fetchOne(@NotNull QueryScheduler scheduler) {
        CompletableFuture<T> completable = new CompletableFuture<>();
        EntityMapper<T> mapper = EntityMapperRegistry.get(clazz);

        scheduler.queue((connection -> {
            try (PreparedStatement statement = connection.prepareStatement(build())) {
                for (int i = 0; i < parameters.size(); ++i) {
                    statement.setObject(i + 1, parameters.get(i));
                }

                try (ResultSet rs = statement.executeQuery()) {
                    completable.complete(rs.next() ? mapper.toEntity(rs) : null);
                } catch (IOException e) {
                    Log.debug("Could not perform SQL fetchOne query, failed to convert entity.", e);
                }
            } catch (SQLException e) {
                Log.debug("Could not perform SQL fetchOne query, something went wrong.", e);
            }
        }));

        return completable;
    }


    @NotNull
    public CompletableFuture<List<T>> fetchAll(@NotNull QueryScheduler scheduler) {
        CompletableFuture<List<T>> completable = new CompletableFuture<>();
        EntityMapper<T> mapper = EntityMapperRegistry.get(clazz);

        scheduler.queue((connection -> {
            try (PreparedStatement statement = connection.prepareStatement(build())) {
                for (int i = 0; i < parameters.size(); ++i) {
                    statement.setObject(i + 1, parameters.get(i));
                }

                try (ResultSet rs = statement.executeQuery()) {
                    List<T> results = new ArrayList<>();
                    while (rs.next()) {
                        results.add(mapper.toEntity(rs));
                    }
                    completable.complete(results);
                } catch (IOException e) {
                    Log.debug("Could not perform SQL fetchAll query, failed to convert entity.", e);
                }
            } catch (SQLException e) {
                Log.debug("Could not perform SQL fetchAll query, something went wrong.", e);
            }
        }));

        return completable;
    }

}
