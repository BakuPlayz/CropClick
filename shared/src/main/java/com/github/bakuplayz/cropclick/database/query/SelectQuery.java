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

import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.database.DatabaseProtocol;
import com.github.bakuplayz.cropclick.database.EntityMapper;
import com.github.bakuplayz.cropclick.database.EntityMapperRegistry;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
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


    /**
     * Should not be called, as we expect item(s) to be returned, and it therefore
     * doesn't make sense to call an execution method to not retrieve anything upon
     * trying to select.
     *
     * @throws UnsupportedOperationException always, as executing select queries doesn't make sense since we want actual items.
     */
    @Override
    public CompletableFuture<Boolean> queue(@NotNull QueryScheduler scheduler) throws UnsupportedOperationException {
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


    /**
     * Adds a WHERE clause that compares one or more values against a field within a JSON column,
     * adapting the query syntax depending on the underlying database protocol.
     *
     * @param protocol the database dialect (e.g. POSTGRESQL, MYSQL, MARIADB).
     * @param column   the JSON key to extract from the data column.
     * @param operator the comparison operator, e.g. "=" or "!=".
     * @param values   the JSON-formatted value(s) to compare against.
     *
     * @return the query instance.
     */
    public SelectQuery<T> whereJSON(@NotNull DatabaseProtocol protocol, @NotNull String column, @NotNull String operator, String @NotNull ... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one value must be provided for whereJSON.");
        }

        query.append(" WHERE ");

        switch (protocol) {
            case POSTGRES:
                if (values.length == 1) {
                    query.append("(data->'").append(column).append("')::text ")
                            .append(operator).append(" ?");
                } else {
                    query.append("(data->'").append(column).append("')::text ");
                    if (operator.equals("=")) {
                        query.append("IN (")
                                .append(repeatPlaceholders(values.length)).append(")");
                    } else if (operator.equals("!=")) {
                        query.append("NOT IN (")
                                .append(repeatPlaceholders(values.length)).append(")");
                    } else {
                        throw new UnsupportedOperationException("Operator not supported for multiple values in Postgres: " + operator);
                    }
                }
                break;

            case MYSQL:
            case MARIADB:
                if (values.length == 1) {
                    if (operator.equals("=")) {
                        query.append("JSON_CONTAINS(JSON_EXTRACT(data, '$.")
                                .append(column).append("'), ?)");
                    } else {
                        query.append("JSON_UNQUOTE(JSON_EXTRACT(data, '$.")
                                .append(column).append("')) ").append(operator).append(" ?");
                    }
                } else {
                    if (operator.equals("=")) {
                        for (int i = 0; i < values.length; i++) {
                            if (i > 0) query.append(" OR ");
                            query.append("JSON_CONTAINS(JSON_EXTRACT(data, '$.")
                                    .append(column).append("'), ?)");
                        }
                    } else if (operator.equals("!=")) {
                        query.append("JSON_UNQUOTE(JSON_EXTRACT(data, '$.")
                                .append(column).append("')) NOT IN (")
                                .append(repeatPlaceholders(values.length)).append(")");
                    } else {
                        query.append("JSON_UNQUOTE(JSON_EXTRACT(data, '$.")
                                .append(column).append("')) IN (")
                                .append(repeatPlaceholders(values.length)).append(")");
                    }
                }
                break;
        }

        Collections.addAll(parameters, values);
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


    @NotNull
    private String repeatPlaceholders(int count) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < count; ++i) {
            joiner.add("?");
        }
        return joiner.toString();
    }

}
