package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datacontainers.services.DataService;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service interface for managing Autofarms.
 * <p>
 * Provides asynchronous operations for retrieving, inserting, updating, and deleting
 * Autofarm entities.
 * </p>
 */
public interface AutofarmDataService extends DataService<Autofarm> {

    /**
     * Gets a list of autofarms, starting from the specified position,
     * and returning up to the provided maximum number.
     *
     * @param start the index from which to begin getting autofarms.
     * @param max   the maximum number of autofarms to retrieve.
     *
     * @return a {@link CompletableFuture} that completes with a list of autofarms, up to the specified max, or fewer if none remain.
     */
    @NotNull
    @Override
    CompletableFuture<List<Autofarm>> getMany(int start, int max);


    /**
     * Gets the autofarm with the specified ID, if it exists.
     *
     * @param id the unique identifier of the autofarm to get.
     *
     * @return a {@link CompletableFuture} that completes with the autofarm if found, otherwise null.
     */
    @NotNull
    @Override
    CompletableFuture<Autofarm> getOne(@NotNull String id);


    /**
     * Inserts a new autofarm into the system.
     *
     * @param autofarm the autofarm object to insert.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if insertion was successful, or {@code false} otherwise.
     */
    @NotNull
    @Override
    CompletableFuture<Boolean> insertOne(@NotNull Autofarm autofarm);


    /**
     * Deletes the autofarm with the specified ID.
     *
     * @param id the unique identifier of the autofarm to delete.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if deletion was successful, or {@code false} otherwise.
     */
    @NotNull
    @Override
    CompletableFuture<Boolean> deleteOne(@NotNull String id);


    /**
     * Updates the autofarm with the specified ID using the provided data.
     *
     * @param id       the unique identifier of the autofarm to update.
     * @param autofarm the autofarm object containing updated data.
     *
     * @return a {@link CompletableFuture} that completes with {@code true} if the update was successful, or {@code false} otherwise.
     */
    @NotNull
    @Override
    CompletableFuture<Boolean> updateOne(@NotNull String id, @NotNull Autofarm autofarm);


    /**
     * Gets the autofarm with the specified crop location, if it exists.
     *
     * @param location the unique identifier of the autofarm to get.
     *
     * @return a {@link CompletableFuture} that completes with the autofarm if found, otherwise null.
     */
    @NotNull
    CompletableFuture<Autofarm> getOneByCrop(@NotNull Location location);


    /**
     * Gets the autofarm with the specified dispenser location, if it exists.
     *
     * @param location the unique identifier of the autofarm to get.
     *
     * @return a {@link CompletableFuture} that completes with the autofarm if found, otherwise null.
     */
    @NotNull
    CompletableFuture<Autofarm> getOneByDispenser(@NotNull Location location);

}
