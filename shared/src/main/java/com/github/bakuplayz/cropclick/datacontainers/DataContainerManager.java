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
package com.github.bakuplayz.cropclick.datacontainers;

import com.github.bakuplayz.cropclick.sql.Column;
import com.github.bakuplayz.cropclick.sql.ConnectionPool;
import com.github.bakuplayz.cropclick.sql.query.DeleteQuery;
import com.github.bakuplayz.cropclick.sql.query.SelectQuery;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

// TODO: Document
@Getter
public final class DataContainerManager {

    //   private final DataContainer<FarmWorld> worldContainer;

    // private final DataContainer<Autofarm> autofarmContainer;


    public DataContainerManager(@NotNull ConnectionPool pool) {
        Object result = new SelectQuery<>(
                new Column<>("name", String.class),
                new Column<>("id", String.class)
        ).fetchOne(pool, Object.class);

        new DeleteQuery<>("hello")
                .where(new Column<>("name", String.class), "=", "hej")
                .execute(pool);

        ;

        //ContainerMode mode = load(config);

        /*this.autofarmContainer = new DataContainer<>(
                mode == ContainerMode.REMOTE
                        ? new DataContainer.RemoteAutofarmService(connect(config))
                        : new DataContainer.LocalAutofarmService()
        );*/
        //  this.worldContainer = new DataContainer<>();
    }


    /*private ContainerMode load(@NotNull DatabaseConfig config) {
        Connection conn = connect(config);

        if (conn == null) {
            return ContainerMode.LOCAL;
        }

        tryClose(conn, 3);
        return ContainerMode.REMOTE;
    }


    @Nullable
    private Connection connect(@NotNull DatabaseConfig config) {
        String host = config.get(ConfigurationKey.HOST);
        String port = config.get(ConfigurationKey.PORT);
        String password = config.get(ConfigurationKey.PASSWORD);
        String username = config.get(ConfigurationKey.USERNAME);
        String database = config.get(ConfigurationKey.DATABASE);
        DatabaseProtocol protocol = config.get(ConfigurationKey.PROTOCOL);

        return tryConnect(String.format("jdbc:%s://%s:%s/%s", protocol.getName(), host, port, database), username, password, 3);
    }


    @Nullable
    private Connection tryConnect(@NotNull String path, @NotNull String username, @NotNull String password, int tries) {
        if (tries <= 0) {
            return null;
        }

        try {
            return DriverManager.getConnection(path, username, password);
        } catch (SQLException e) {
            // TODO: Add error message or handler inside of this (event?)
            return tryConnect(path, username, password, tries - 1);
        }
    }


    private void tryClose(@NotNull Connection connection, int tries) {
        if (tries <= 0) {
            // TODO write an error.
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            tryClose(connection, tries - 1);
        }
    }


    enum ContainerMode {
        LOCAL,
        REMOTE;
    }*/

}
