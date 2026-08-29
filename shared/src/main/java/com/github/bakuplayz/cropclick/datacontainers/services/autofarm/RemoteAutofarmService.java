package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractRemoteDataService;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.bakuplayz.spigotstore.persistence.sql.api.QueryProvider;
import dev.bakuplayz.spigotstore.persistence.sql.api.SelectQuery;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Remote implementation of {@link AutofarmDataService} that communicates with an external database
 * configured within the database configuration files.
 */
public final class RemoteAutofarmService extends AbstractRemoteDataService<Autofarm> implements AutofarmDataService {

    private final AsyncLoadingCache<LookupKey, Autofarm> locationCache;


    public RemoteAutofarmService(@NotNull QueryProvider provider) {
        super(provider, Autofarm.class);
        this.locationCache = initializeLocationCache();
    }


    @NotNull
    private AsyncLoadingCache<LookupKey, Autofarm> initializeLocationCache() {
        return Caffeine.newBuilder()
                       .maximumSize(MAX_CACHE_ENTRIES)
                       .expireAfterWrite(10, TimeUnit.MINUTES)
                       .expireAfterAccess(10, TimeUnit.MINUTES)
                       .buildAsync((key, executor) -> loadLocationFromDatabase(key));
    }


    @NotNull
    private CompletableFuture<Autofarm> loadLocationFromDatabase(@NotNull LookupKey key) {
        SelectQuery<Autofarm> query = provider.select(getTable(), Autofarm.class);

        if (key.isDoubly()) {
            return query.whereJSON("container", "=",
                    key.getSingly(),
                    key.getDoubly()
            ).fetchOne();
        }

        return query.whereJSON(key.getType().getColumn(), "=", key.getSingly()).fetchOne();
    }


    @NotNull
    @Override
    protected String getTable() {
        return "autofarms";
    }


    @NotNull
    @Override
    protected String getDefaultIdentifier() {
        return "farmer";
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByCrop(@NotNull Location location) {
        return locationCache.get(LookupKey.of(LookupType.CROP, location));
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByDispenser(@NotNull Location location) {
        return locationCache.get(LookupKey.of(LookupType.DISPENSER, location));
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByContainer(@NotNull Location location) {
        return locationCache.get(LookupKey.of(LookupType.CONTAINER, location));
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
    @EqualsAndHashCode
    @RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    private static final class LookupKey {

        @NotNull
        private final LookupType type;

        @NotNull
        private final LocationKey singly;

        @Nullable
        private final LocationKey doubly;


        @NotNull
        public static LookupKey of(@NotNull LookupType type, @NotNull Location location) {
            if (location instanceof DoublyLocation) {
                return new LookupKey(
                        type,
                        LocationKey.from(((DoublyLocation) location).getSingly()),
                        LocationKey.from(((DoublyLocation) location).getDoubly())
                );
            } else {
                return new LookupKey(type, LocationKey.from(location), null);
            }
        }


        public boolean isDoubly() {
            return doubly != null;
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    private static final class LocationKey {

        private final int x, y, z;

        @NotNull
        private final String world;


        @NotNull
        public static LocationKey from(@NotNull Location location) {
            return new LocationKey(
                    location.getBlockX(),
                    location.getBlockY(),
                    location.getBlockZ(),
                    location.getWorld().getName()
            );
        }
    }


}
