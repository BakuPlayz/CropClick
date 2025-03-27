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

import com.github.bakuplayz.cropclick.sql.Column;
import com.github.bakuplayz.cropclick.sql.ConnectionPool;
import com.github.bakuplayz.cropclick.sql.RowMapper;
import com.github.bakuplayz.cropclick.sql.RowMapperRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * A class representing a typed SQL select query, where the type
 * T is the type of the result of performing the query.
 *
 * @param <T> the resulting type, after querying.
 */
public final class SelectQuery<T> extends BaseQuery<T> {

    @SafeVarargs
    public SelectQuery(Column<T, ?> @NotNull ... columns) {
        query.append("SELECT ");
        StringJoiner joiner = new StringJoiner(", ");
        for (Column<T, ?> column : columns) {
            joiner.add(column.getName());
        }
        query.append(joiner);
    }


    public SelectQuery<T> from(@NotNull String table) {
        query.append(" FROM ").append(table);
        return this;
    }


    public SelectQuery<T> limit(int count) {
        query.append(" LIMIT ?");
        parameters.add(count);
        return this;
    }


    @Nullable
    public T fetchOne(@NotNull ConnectionPool pool, Class<T> clazz) {
        RowMapper<T> mapper = RowMapperRegistry.get(clazz);
        Optional<Connection> optionalConn = pool.acquire();

        if (!optionalConn.isPresent()) {
            return null;
        }

        Connection conn = optionalConn.get();
        try (PreparedStatement stmt = conn.prepareStatement(build())) {
            for (int i = 0; i < parameters.size(); ++i) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapper.map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query.", e);
        } finally {
            pool.release(conn);
        }
    }


    @NotNull
    public List<T> fetchAll(@NotNull ConnectionPool pool, Class<T> clazz) {
        RowMapper<T> mapper = RowMapperRegistry.get(clazz);
        Optional<Connection> optionalConn = pool.acquire();

        if (!optionalConn.isPresent()) {
            return Collections.emptyList();
        }

        Connection conn = optionalConn.get();
        try (PreparedStatement stmt = conn.prepareStatement(build())) {
            for (int i = 0; i < parameters.size(); ++i) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query.", e);
        } finally {
            pool.release(conn);
        }
    }

}
