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

import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import com.github.bakuplayz.cropclick.datacontainers.services.world.FarmWorldDataService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


// TODO: Document
@Getter
public final class DataServiceManager {

    private final AutofarmDataService autofarmService;

    private final FarmWorldDataService farmWorldDataService;


    public DataServiceManager(@NotNull DataServiceFactory factory) {
        this.autofarmService = factory.createAutofarmService();
        this.farmWorldDataService = factory.createFarmWorldService();
    }

}
