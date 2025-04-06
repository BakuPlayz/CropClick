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
package com.github.bakuplayz.cropclick.datacontainers;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.sql.QueryScheduler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.datacontainers.DataContainer.LocalAutofarmService;
import static com.github.bakuplayz.cropclick.datacontainers.DataContainer.RemoteAutofarmService;

// TODO: Document
@Getter
public final class DataContainerManager {

    //   private final DataContainer<FarmWorld> worldContainer;

    private final DataContainer<Autofarm> autofarmContainer;


    public DataContainerManager(@NotNull QueryScheduler scheduler) {
     /*   CompletableFuture<Object> result = new SelectQuery<>(
                new Column<>("name", String.class),
                new Column<>("id", String.class)
        ).fetchOne(scheduler, Object.class);

        new DeleteQuery<>("hello")
                .where(new Column<>("name", String.class), "=", "hej")
                .execute(scheduler);*/

        this.autofarmContainer = new DataContainer<>(
                scheduler.canQuery()
                        ? new RemoteAutofarmService(scheduler)
                        : new LocalAutofarmService()
        );
        /*this.autofarmContainer = new DataContainer<>(
                mode == ContainerMode.REMOTE
                        ? new DataContainer.RemoteAutofarmService(connect(config))
                        : new DataContainer.LocalAutofarmService()
        );*/
        //  this.worldContainer = new DataContainer<>();
    }

}
