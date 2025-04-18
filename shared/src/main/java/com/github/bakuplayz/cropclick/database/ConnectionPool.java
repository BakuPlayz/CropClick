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
package com.github.bakuplayz.cropclick.database;

import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import static com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig.DatabaseProtocol;

/**
 *
 */
public final class ConnectionPool {

    private final static int MAX_POOL_SIZE = 10;

    private final static int MAX_CONNECTION_TRIES = 3;


    private final Semaphore connectionsSema;

    private final Queue<Connection> connections;


    public ConnectionPool(@NotNull DatabaseConfig config) {
        this.connections = initializePool(config);
        this.connectionsSema = new Semaphore(MAX_POOL_SIZE);
    }


    /**
     * Acquires a new connection once it has become available,
     * will sleep the thread while waiting for one or just return
     * one immediately.
     *
     * @return the acquired connection.
     *
     * @throws InterruptedException since there is a sema wait.
     */
    public Connection acquire() throws InterruptedException {
        connectionsSema.acquire();

        synchronized (connections) {
            return connections.poll();
        }
    }


    /**
     * Releases the provided connection back to the pool again.
     *
     * @param connection the connection that is to be released.
     */
    public void release(@NotNull Connection connection) {
        try {
            if (connection.isClosed()) return;
        } catch (SQLException e) {
            Log.debug("Could not release an established SQL connection, already closed.", e);
        }

        synchronized (connections) {
            connections.add(connection);
        }

        connectionsSema.release();
    }


    /**
     * Closes all the connections within the pool, waits in all
     * the connections to be closed before closing and removing all
     * connections.
     *
     * @throws InterruptedException since there is a sema wait.
     */
    public void close() throws InterruptedException {
        connectionsSema.acquire(MAX_POOL_SIZE);

        synchronized (connections) {
            for (Iterator<Connection> it = connections.iterator(); it.hasNext(); ) {
                try {
                    it.next().close();
                    it.remove();
                } catch (SQLException e) {
                    Log.debug("Could not close an established SQL connection.", e);
                }
            }
        }

        connectionsSema.drainPermits();
    }


    /**
     * Checks if there are any established connections.
     *
     * @return true iff any, false otherwise.
     */
    public boolean isEstablished() {
        return !connections.isEmpty();
    }


    @NotNull
    private Queue<Connection> initializePool(@NotNull DatabaseConfig config) {
        Queue<Connection> pool = new LinkedList<>();

        for (int i = 0; i < MAX_POOL_SIZE; ++i) {
            pool.add(connect(config));
        }

        return pool;
    }


    @Nullable
    private Connection connect(@NotNull DatabaseConfig config) {
        String host = config.get(DatabaseConfig.ConfigurationKey.HOST);
        String port = config.get(DatabaseConfig.ConfigurationKey.PORT);
        String password = config.get(DatabaseConfig.ConfigurationKey.PASSWORD);
        String username = config.get(DatabaseConfig.ConfigurationKey.USERNAME);
        String database = config.get(DatabaseConfig.ConfigurationKey.DATABASE);
        DatabaseProtocol protocol = config.get(DatabaseConfig.ConfigurationKey.PROTOCOL);

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
            Log.info(String.format("Could not establish SQL connections successfully, retrying (%d/%d).", tries, MAX_CONNECTION_TRIES), e);
            return tryConnect(path, username, password, tries - 1);
        }
    }


}
