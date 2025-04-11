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
import com.github.bakuplayz.cropclick.datacontainers.services.DataService;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.LocalAutofarmService;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.RemoteAutofarmService;
import com.github.bakuplayz.cropclick.datacontainers.services.world.FarmWorldDataService;
import com.github.bakuplayz.cropclick.datacontainers.services.world.LocalFarmWorldService;
import com.github.bakuplayz.cropclick.datacontainers.services.world.RemoteFarmWorldService;
import com.github.bakuplayz.cropclick.sql.ConnectionPool;
import com.github.bakuplayz.cropclick.sql.QueryScheduler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * A manager controlling all the {@link DataService data services}.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
@Getter
public final class DataServiceManager {

    private final QueryScheduler scheduler;

    private final AutofarmDataService autofarmService;

    private final FarmWorldDataService farmWorldDataService;


    public DataServiceManager(@NotNull CropClick plugin) {
        this.autofarmService = createAutofarmService();
        this.farmWorldDataService = createFarmWorldService();
        this.scheduler = new QueryScheduler(
                new ConnectionPool(plugin.getConfigManager().getDatabaseConfig())
        );
    }


    @NotNull
    public AutofarmDataService createAutofarmService() {
        return scheduler.canQuery()
                       ? new RemoteAutofarmService(scheduler)
                       : new LocalAutofarmService();
    }


    @NotNull
    public FarmWorldDataService createFarmWorldService() {
        return scheduler.canQuery()
                       ? new RemoteFarmWorldService(scheduler)
                       : new LocalFarmWorldService();
    }

}
