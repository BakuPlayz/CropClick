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
package com.github.bakuplayz.cropclick.datacontainers.services.world;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractLocalDataService;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Local in-memory implementation of {@link FarmWorldDataService}, used when there
 * is no database configured.
 */
public final class LocalFarmWorldService extends AbstractLocalDataService<FarmWorld> implements FarmWorldDataService {

    public LocalFarmWorldService() {
        super("worlds.json", new TypeReference<Map<String, FarmWorld>>() {
        });
    }


    @Override
    protected String getDefaultIdentifier(@NotNull FarmWorld world) {
        return world.getName();
    }

}
