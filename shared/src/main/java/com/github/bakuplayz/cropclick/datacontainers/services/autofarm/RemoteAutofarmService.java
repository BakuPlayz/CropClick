package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractRemoteDataService;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.bakuplayz.spigotstore.database.QueryScheduler;
import dev.bakuplayz.spigotstore.database.query.SelectQuery;
import dev.bakuplayz.spigotstore.database.query.providers.QueryProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Remote implementation of {@link AutofarmDataService} that communicates with an external database
 * configured within the database configuration files.
 */
public final class RemoteAutofarmService extends AbstractRemoteDataService<Autofarm> implements AutofarmDataService {

    private static final long MAX_CACHE_ENTRIES = 1000;

    private final AsyncLoadingCache<LookupKey, Autofarm> cache;


    public RemoteAutofarmService(@NotNull QueryScheduler scheduler, @NotNull QueryProvider provider) {
        super(scheduler, provider, Autofarm.class);
        this.cache = initializeCache();
    }


    @NotNull
    private AsyncLoadingCache<LookupKey, Autofarm> initializeCache() {
        return Caffeine.newBuilder()
                       .maximumSize(MAX_CACHE_ENTRIES)
                       .expireAfterAccess(10, TimeUnit.MINUTES)
                       .buildAsync((key, executor) -> loadFromDatabase(key));
    }


    @NotNull
    private CompletableFuture<Autofarm> loadFromDatabase(@NotNull LookupKey key) {
        SelectQuery<Autofarm> query = provider.select(getTable(), Autofarm.class);

        if (key.isDoubly()) {
            return query.whereJSON("container", "=",
                    ((DoublyLocation) key.getLocation()).getSingly(),
                    ((DoublyLocation) key.getLocation()).getDoubly()
            ).fetchOne(scheduler);
        }

        return query.whereJSON(key.getType().getColumn(), "=", key.getLocation()).fetchOne(scheduler);
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
        return cache.get(LookupKey.of(LookupType.CROP, location));
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByDispenser(@NotNull Location location) {
        return cache.get(LookupKey.of(LookupType.DISPENSER, location));
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByContainer(@NotNull Location location) {
        return cache.get(LookupKey.of(LookupType.CONTAINER, location));
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private enum LookupType {

        CROP,

        CONTAINER,

        DISPENSER;


        @NotNull
        public String getColumn() {
            return name().toLowerCase();
        }

    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class LookupKey {

        @NotNull
        private final LookupType type;

        @NotNull
        private final Location location;


        @NotNull
        public static LookupKey of(@NotNull LookupType type, @NotNull Location location) {
            return new LookupKey(type, location);
        }


        public boolean isDoubly() {
            return type == LookupType.CONTAINER && location instanceof DoublyLocation;
        }

    }

}
