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
package com.github.bakuplayz.cropclick.datacontainers.services.world;

import com.github.bakuplayz.cropclick.datacontainers.services.DataService;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service interface for managing FarmWorlds.
 * <p>
 * Provides asynchronous operations for retrieving, inserting, updating, and deleting
 * FarmWorld entities.
 * </p>
 */
public interface FarmWorldDataService extends DataService<FarmWorld> {

    /**
     * Gets a list of worlds, starting from the specified position,
     * and returning up to the provided maximum number.
     *
     * @param start the index from which to begin getting worlds.
     * @param max   the maximum number of worlds to retrieve.
     *
     * @return a {@link CompletableFuture} that completes with a list of worlds, up to the specified max, or fewer if none remain.
     */
    @NotNull
    @Override
    CompletableFuture<List<FarmWorld>> getMany(int start, int max);


    /**
     * Gets the world with the specified name, if it exists.
     *
     * @param name the unique name of the world to get.
     *
     * @return a {@link CompletableFuture} that completes with the world if found, otherwise null.
     */
    @NotNull
    @Override
    CompletableFuture<FarmWorld> getOne(@NotNull String name);


    /**
     * Inserts a new world into the system.
     *
     * @param world the world object to insert.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if insertion was successful, or {@code false} otherwise.
     */
    @NotNull
    @Override
    CompletableFuture<Boolean> insertOne(@NotNull FarmWorld world);


    /**
     * Deletes the world with the specified name.
     *
     * @param name the unique name of the world to delete.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if deletion was successful, or {@code false} otherwise.
     */
    @NotNull
    @Override
    CompletableFuture<Boolean> deleteOne(@NotNull String name);


    /**
     * Updates the world with the specified name using the provided data.
     *
     * @param name  the unique name of the world to update.
     * @param world the world object containing updated data.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the update was successful, or {@code false} otherwise.
     */
    @NotNull
    @Override
    CompletableFuture<Boolean> updateOne(@NotNull String name, @NotNull FarmWorld world);


    /**
     * Counts all entities inside the data container.
     *
     * @return a {@link CompletableFuture} that completes with the amount of entities.
     */
    @NotNull
    @Override
    CompletableFuture<Integer> countAll();

}
