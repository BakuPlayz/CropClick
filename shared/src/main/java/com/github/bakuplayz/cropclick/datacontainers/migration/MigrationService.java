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
package com.github.bakuplayz.cropclick.datacontainers.migration;

import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.configurations.config.UsageConfig;
import com.github.bakuplayz.cropclick.datacontainers.services.DataService;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.UsageConfig.ConfigurationKey;
import static com.github.bakuplayz.cropclick.datacontainers.DataServiceManager.LiveServiceContext;
import static com.github.bakuplayz.cropclick.datacontainers.DataServiceManager.MigrationServiceContext;


@RequiredArgsConstructor
public final class MigrationService {

    private final LiveServiceContext liveContext;

    private final MigrationServiceContext migrationContext;

    private final UsageConfig config;


    public void start() {
        config.set(ConfigurationKey.DATABASES_MIGRATION_STATUS, MigrationStatus.IN_PROGRESS);

        migrationContext.start();

        AutofarmDataService fromFarmService = liveContext.getAutofarmService();
        AutofarmDataService toFarmService = migrationContext.getAutofarmService();
        if (toFarmService == null) {
            Log.info("Failed to migrate please check your config, restart and try again.");
            config.set(ConfigurationKey.DATABASES_MIGRATION_STATUS, MigrationStatus.FAILED);
            migrationContext.shutdown();
            return;
        }

        migrateAllEntities(fromFarmService, toFarmService);

        config.set(ConfigurationKey.DATABASES_MIGRATION_STATUS, MigrationStatus.COMPLETED);

        migrationContext.swap(liveContext);

        migrationContext.shutdown();
    }


    private <E> void migrateAllEntities(@NotNull DataService<E> from, @NotNull DataService<E> to) {
        from.getMany(0, Integer.MAX_VALUE)
                .thenAccept(entities -> entities.forEach(entity -> migrateEntity(entity, to)))
                .exceptionally(ex -> {
                    Log.severe("Failed to migrate entities from service to service.", ex);
                    return null;
                });
    }


    private <E> void migrateEntity(@NotNull E entity, @NotNull DataService<E> to) {
        to.insertOne(entity).exceptionally(ex -> {
            Log.debug("Failed to migrate {0} entity.", entity.toString());
            return null;
        });
    }

}
