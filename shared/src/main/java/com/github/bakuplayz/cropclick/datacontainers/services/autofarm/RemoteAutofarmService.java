package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.location.DoublyLocation;
import com.github.bakuplayz.cropclick.database.DatabaseProtocol;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import com.github.bakuplayz.cropclick.database.query.SelectQuery;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractRemoteDataService;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig.ConfigurationKey;

/**
 * Remote implementation of {@link AutofarmDataService} that communicates with an external database
 * configured within the database configuration files.
 */
public final class RemoteAutofarmService extends AbstractRemoteDataService<Autofarm> implements AutofarmDataService {

    private final DatabaseProtocol protocol;

    private final ObjectMapper mapper;


    public RemoteAutofarmService(@NotNull CropClick plugin, @NotNull QueryScheduler scheduler) {
        super(scheduler, Autofarm.class);
        this.protocol = plugin.getConfigManager().getDatabaseConfig().get(ConfigurationKey.PROTOCOL);
        this.mapper = plugin.getDatabaseManager().getJsonMapper();
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
        try {
            return new SelectQuery<>(getTable(), Autofarm.class)
                           .whereJSON(protocol, "crop", "=", mapper.writeValueAsString(location))
                           .fetchOne(scheduler);
        } catch (JsonProcessingException e) {
            Log.debug("Failed to find crop by location.", e);
            return CompletableFuture.completedFuture(null);
        }
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByDispenser(@NotNull Location location) {
        try {
            return new SelectQuery<>(getTable(), Autofarm.class)
                           .whereJSON(protocol, "dispenser", "=", mapper.writeValueAsString(location))
                           .fetchOne(scheduler);
        } catch (JsonProcessingException e) {
            Log.debug("Failed to find dispenser by location.", e);
            return CompletableFuture.completedFuture(null);
        }
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByContainer(@NotNull Location location) {
        try {
            SelectQuery<Autofarm> query = new SelectQuery<>(getTable(), Autofarm.class);
            if (location instanceof DoublyLocation) {
                return query.whereJSON(protocol, "container", "=",
                        mapper.writeValueAsString(location),
                        mapper.writeValueAsString(((DoublyLocation) location).getDoubly())
                ).fetchOne(scheduler);
            }
            return query.whereJSON(protocol, "container", "=", mapper.writeValueAsString(location)).fetchOne(scheduler);
        } catch (JsonProcessingException e) {
            Log.debug("Failed to find container by location.", e);
            return CompletableFuture.completedFuture(null);
        }
    }

}
