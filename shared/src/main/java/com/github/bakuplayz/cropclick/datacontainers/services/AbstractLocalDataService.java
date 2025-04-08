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

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Abstract base class for in-memory data service implementations.
 * <p>
 * Subclasses must provide a way to extract a unique identifier from entities of type {@code D}
 * by implementing {@link #getIdentifier(Object)}.
 * </p>
 *
 * @param <D> the type of data entity managed by this service.
 */
@AllArgsConstructor
public abstract class AbstractLocalDataService<D> {

    private final HashMap<String, D> data;


    public AbstractLocalDataService() {
        this.data = new HashMap<>();
    }


    /**
     * Extracts the unique identifier from the given entity.
     *
     * @param entity the entity from which to extract the identifier.
     *
     * @return a string identifier.
     */
    protected abstract String getIdentifier(@NotNull D entity);


    /**
     * Gets a paginated list of entities from the in-memory store.
     *
     * @param start the starting index of entities to get.
     * @param max   the maximum number of entities to return.
     *
     * @return a {@link CompletableFuture} containing the found list of entities.
     */
    @NotNull
    public CompletableFuture<List<D>> getMany(int start, int max) {
        return CompletableFuture.completedFuture(
                data.values().stream()
                        .skip(start)
                        .limit(max)
                        .collect(Collectors.toList())
        );
    }


    /**
     * Gets a single entity by its ID.
     *
     * @param id the unique identifier of the entity to get.
     *
     * @return a {@link CompletableFuture} containing the entity, or {@code null} if not found.
     */
    @NotNull
    public CompletableFuture<D> getOne(@NotNull String id) {
        return CompletableFuture.completedFuture(data.get(id));
    }


    /**
     * Inserts a new entity into the data store if it does not already exist.
     *
     * @param entity the entity to insert.
     *
     * @return a {@link CompletableFuture} that completes with {@code true}.
     */
    @NotNull
    public CompletableFuture<Boolean> insertOne(@NotNull D entity) {
        data.putIfAbsent(getIdentifier(entity), entity);
        return CompletableFuture.completedFuture(true);
    }


    /**
     * Deletes an entity from the data store by its ID.
     *
     * @param id the unique identifier of the entity to delete.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the entity was removed,
     * or {@code false} if it did not exist.
     */
    @NotNull
    public CompletableFuture<Boolean> deleteOne(@NotNull String id) {
        return CompletableFuture.completedFuture(data.remove(id) != null);
    }


    /**
     * Updates an entity in the data store with the given ID.
     *
     * @param id     the unique identifier of the entity to update.
     * @param entity the new entity data.
     *
     * @return a {@link CompletableFuture} that completes with {@code true}.
     */
    @NotNull
    public CompletableFuture<Boolean> updateOne(@NotNull String id, @NotNull D entity) {
        data.put(id, entity);
        return CompletableFuture.completedFuture(true);
    }


    /**
     * TODO: Implement this!!!!
     * <p>
     * Persists the current in-memory data to a storage medium.
     *
     * @return a {@link CompletableFuture} indicating whether the save operation was successful.
     *
     * @throws UnexpectedException if not implemented.
     */
    public CompletableFuture<Boolean> save() throws UnexpectedException {
        throw new UnexpectedException("Not implemented yet");
    }
}

