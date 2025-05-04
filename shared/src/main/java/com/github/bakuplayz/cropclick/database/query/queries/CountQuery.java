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

import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public final class CountQuery extends BaseQuery {

    public CountQuery(@NotNull String table) {
        query.append("SELECT COUNT(*) FROM ").append(table);
    }


    @NotNull
    public CompletableFuture<Integer> queue(@NotNull QueryScheduler scheduler) {
        CompletableFuture<Integer> completable = new CompletableFuture<>();

        scheduler.queue((connection) -> {
            // Note: Ignoring the following security error due to it already using (?) whenever
            // permuting the query. So there is no injectable queries being created.
            try (PreparedStatement statement = connection.prepareStatement(build())) {
                for (int i = 0; i < parameters.size(); ++i) {
                    statement.setObject(i + 1, parameters.get(i));
                }

                try (ResultSet rs = statement.executeQuery()) {
                    completable.complete(rs.next() ? rs.getInt(1) : null);
                }
            } catch (SQLException e) {
                Log.debug("Could not perform SQL update query, something went wrong.", e);
                completable.complete(-1);
            }
        });

        return completable;
    }

}
