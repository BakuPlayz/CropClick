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
package com.github.bakuplayz.cropclick.datacontainers.services;

import dev.bakuplayz.spigotstore.database.QueryScheduler;
import dev.bakuplayz.spigotstore.database.query.providers.QueryProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * Remote implementation of {@link DataService} that communicates with an external database
 * configured within the database configuration files, using generics to handle whatever data
 * type is provided.
 *
 * @param <D> the type of data entity managed by this service.
 */
public abstract class AbstractRemoteDataService<D> implements DataService<D> {

    protected final QueryProvider provider;

    protected final QueryScheduler scheduler;

    private final Class<D> clazz;


    public AbstractRemoteDataService(@NotNull QueryScheduler scheduler, @NotNull QueryProvider provider, @NotNull Class<D> clazz) {
        this.scheduler = scheduler;
        this.provider = provider;
        this.clazz = clazz;
        createTable();
    }


    /**
     * Creates table if not already exists.
     */
    private void createTable() {
        provider.create(getTable(), clazz, true).queue(scheduler);
    }


    /**
     * Gets the database table associated with this entity.
     *
     * @return the database table name.
     */
    protected abstract String getTable();


    /**
     * Gets the name of the default identifier column used to uniquely identify entities.
     *
     * @return the identifier column name.
     */
    protected abstract String getDefaultIdentifier();


    /**
     * Gets a paginated list of entities from the remote table.
     *
     * @param start the index of the first entity to get.
     * @param max   the maximum number of entities to return.
     *
     * @return a {@link CompletableFuture} that completes with the list of found entities.
     */
    @NotNull
    public CompletableFuture<List<D>> getMany(int start, int max) {
        return provider.select(getTable(), clazz)
                       .limit(start, max)
                       .fetchAll(scheduler);
    }


    /**
     * Gets a single entity from the remote table by its identifier.
     *
     * @param id the unique identifier of the entity.
     *
     * @return a {@link CompletableFuture} that completes with the entity if found.
     */
    @NotNull
    public CompletableFuture<D> getOne(@NotNull String id) {
        return provider.select(getTable(), clazz)
                       .where(getDefaultIdentifier(), "=", id)
                       .fetchOne(scheduler);
    }


    /**
     * Inserts a new entity into the remote table.
     *
     * @param entity the entity to insert.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the insertion was successful.
     */
    @NotNull
    public CompletableFuture<Boolean> insertOne(@NotNull D entity) {
        return provider.insert(getTable(), clazz, true)
                       .values(entity)
                       .queue(scheduler);
    }


    /**
     * Deletes an entity from the remote table by its identifier.
     *
     * @param id the unique identifier of the entity to delete.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the deletion was successful.
     */
    @NotNull
    public CompletableFuture<Boolean> deleteOne(@NotNull String id) {
        return provider.delete(getTable())
                       .where(getDefaultIdentifier(), "=", id)
                       .queue(scheduler);
    }


    /**
     * Updates an existing entity in the remote table using the provided identifier and updated data.
     *
     * @param id     the unique identifier of the entity to update.
     * @param entity the new data for the entity.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the update was successful.
     */
    @NotNull
    public CompletableFuture<Boolean> updateOne(@NotNull String id, @NotNull D entity) {
        return provider.update(getTable(), clazz)
                       .where(getDefaultIdentifier(), "=", id)
                       .setAll(entity)
                       .queue(scheduler);
    }


    /**
     * Counts all entities inside the data container.
     *
     * @return a {@link CompletableFuture} that completes with the amount of entities.
     */
    @NotNull
    public CompletableFuture<Integer> countAll() {
        return provider.count(getTable()).queue(scheduler);
    }


    /**
     * Removing all entries from the table, using a delete query on all entries.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the drop was successful.
     */
    public CompletableFuture<Boolean> reset() {
        return provider.delete(getTable())
                       .matchAll()
                       .queue(scheduler);
    }


    // TODO: Implement
    @Override
    public CompletableFuture<Boolean> reload() {
        return CompletableFuture.completedFuture(true);
    }

}
