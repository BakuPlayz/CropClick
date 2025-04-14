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

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generic data service interface for managing entities of type {@code D}.
 * <p>
 * Provides asynchronous operations for retrieving, inserting, updating, and deleting
 * data entities.
 * </p>
 *
 * @param <D> the type of data entity managed by this service.
 */
public interface DataService<D> {

    /**
     * Gets a list of data entities, starting from the specified position,
     * and returning up to the provided maximum number.
     *
     * @param start the index from which to begin retrieving entities.
     * @param max   the maximum number of entities to retrieve.
     *
     * @return a {@link CompletableFuture} that completes with a list of entities,
     * up to the specified max, or fewer if none remain.
     */
    @NotNull
    CompletableFuture<List<D>> getMany(int start, int max);


    /**
     * Get the data entity with the specified ID, if it exists.
     *
     * @param id the unique identifier of the entity to retrieve.
     *
     * @return a {@link CompletableFuture} that completes with the entity if found.
     */
    @NotNull
    CompletableFuture<D> getOne(@NotNull String id);


    /**
     * Inserts a new data entity into the system.
     *
     * @param entity the entity to insert.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if insertion was successful,
     * or {@code false} otherwise.
     */
    @NotNull
    CompletableFuture<Boolean> insertOne(@NotNull D entity);


    /**
     * Deletes the data entity with the specified ID.
     *
     * @param id the unique identifier of the entity to delete.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if deletion was successful,
     * or {@code false} otherwise.
     */
    @NotNull
    CompletableFuture<Boolean> deleteOne(@NotNull String id);


    /**
     * Updates the data entity with the specified ID using the provided updated entity.
     *
     * @param id     the unique identifier of the entity to update.
     * @param entity the entity containing updated data.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the update was successful,
     * or {@code false} otherwise.
     */
    @NotNull
    CompletableFuture<Boolean> updateOne(@NotNull String id, @NotNull D entity);


    // TODO: figure out control flow
    CompletableFuture<?> reset();

}
