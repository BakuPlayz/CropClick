package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.location.DoublyLocation;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import com.github.bakuplayz.cropclick.database.query.QueryProvider;
import com.github.bakuplayz.cropclick.database.query.queries.SelectQuery;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractRemoteDataService;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Remote implementation of {@link AutofarmDataService} that communicates with an external database
 * configured within the database configuration files.
 */
public final class RemoteAutofarmService extends AbstractRemoteDataService<Autofarm> implements AutofarmDataService {


    public RemoteAutofarmService(@NotNull QueryScheduler scheduler, @NotNull QueryProvider provider) {
        super(scheduler, provider, Autofarm.class);
    }


    @NotNull
    @Override
    protected String getTable() {
        return "autofarms";
    }


    @NotNull
    @Override
    protected String getDefaultIdentifier() {
        return "farmer_id";
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByCrop(@NotNull Location location) {
        return provider.select(getTable(), Autofarm.class)
                       .whereJSON("crop", "=", location)
                       .fetchOne(scheduler);
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByDispenser(@NotNull Location location) {
        return provider.select(getTable(), Autofarm.class)
                       .whereJSON("dispenser", "=", location)
                       .fetchOne(scheduler);
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByContainer(@NotNull Location location) {
        SelectQuery<Autofarm> query = provider.select(getTable(), Autofarm.class);
        if (location instanceof DoublyLocation) {
            return query.whereJSON("container", "=",
                    ((DoublyLocation) location).getSingly(),
                    ((DoublyLocation) location).getDoubly()
            ).fetchOne(scheduler);
        }
        return query.whereJSON("container", "=", location).fetchOne(scheduler);
    }

}
