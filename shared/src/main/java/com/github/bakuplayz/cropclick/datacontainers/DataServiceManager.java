/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2025 BakuPlayz
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
package com.github.bakuplayz.cropclick.datacontainers;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datacontainers.migration.MigrationService;
import com.github.bakuplayz.cropclick.datacontainers.services.DataService;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.LocalAutofarmService;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.RemoteAutofarmService;
import com.github.bakuplayz.cropclick.datacontainers.services.world.FarmWorldDataService;
import com.github.bakuplayz.cropclick.datacontainers.services.world.LocalFarmWorldService;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;

import static com.github.bakuplayz.cropclick.database.DatabaseManager.LiveDatabaseContext;
import static com.github.bakuplayz.cropclick.database.DatabaseManager.MigrationDatabaseContext;


/**
 * A manager controlling all the {@link DataService data services}.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */


public final class DataServiceManager {

    @Getter
    private final MigrationService migrationService;

    private final LiveServiceContext liveContext;


    public DataServiceManager(@NotNull CropClick plugin) {
        this.liveContext = new LiveServiceContext(plugin);
        this.migrationService = new MigrationService(liveContext, new MigrationServiceContext(plugin), plugin.getConfigManager().getUsageConfig());
    }


    @NotNull
    public Collection<DataService<?>> getAll() {
        return Arrays.asList(getAutofarmService(), getFarmWorldService());
    }


    public AutofarmDataService getAutofarmService() {
        return liveContext.getAutofarmService();
    }


    public FarmWorldDataService getFarmWorldService() {
        return liveContext.getFarmWorldService();
    }


    @Getter
    public static final class LiveServiceContext {

        @NotNull
        private final CropClick plugin;

        @Getter
        private final LiveDatabaseContext context;

        private final FarmWorldDataService farmWorldService;

        private AutofarmDataService autofarmService;


        public LiveServiceContext(@NotNull CropClick plugin) {
            this.plugin = plugin;
            this.context = plugin.getDatabaseManager().getLiveContext();
            this.farmWorldService = createFarmWorldService();
            this.autofarmService = createAutofarmService();
        }


        @NotNull
        private AutofarmDataService createAutofarmService() {
            if (context.getQueryProvider().isConnected()) {
                return new RemoteAutofarmService(context.getQueryProvider());
            }
            return new LocalAutofarmService();
        }


        @NotNull
        private FarmWorldDataService createFarmWorldService() {
            return new LocalFarmWorldService();
        }

    }

    @Getter
    @AllArgsConstructor
    public static final class MigrationServiceContext {

        @NotNull
        private final CropClick plugin;

        @Getter
        @NotNull
        private final MigrationDatabaseContext context;


        private AutofarmDataService autofarmService;


        public MigrationServiceContext(@NotNull CropClick plugin) {
            this.context = plugin.getDatabaseManager().getMigrationContext();
            this.plugin = plugin;
        }


        public void start() {
            plugin.getStore().getTaskScheduler().runTask(() -> {
                this.context.start();
                this.autofarmService = createAutofarmService();
            }, TaskContext.BACKGROUND);
        }


        public void swap(@NotNull LiveServiceContext context) {
            AutofarmDataService tmpAutofarm = this.autofarmService;
            this.autofarmService = context.autofarmService;
            context.autofarmService = tmpAutofarm;
        }


        public void shutdown() {
            this.autofarmService = null;
        }


        @Nullable
        private AutofarmDataService createAutofarmService() {
            if (context.getQueryProvider().isConnected()) {
                return new RemoteAutofarmService(context.getQueryProvider());
            }
            return null;
        }

    }

}
