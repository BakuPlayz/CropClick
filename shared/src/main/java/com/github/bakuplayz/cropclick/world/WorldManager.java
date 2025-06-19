/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
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

package com.github.bakuplayz.cropclick.world;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datacontainers.services.world.FarmWorldDataService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * A manager holding and controlling all the {@link FarmWorld farm worlds}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 3.0.0
 */
public final class WorldManager {

    private final static int WORLDS_MAX = 1_000;

    private final FarmWorldDataService dataService;

    @Getter
    private final FarmWorldFinder finder;


    public WorldManager(@NotNull CropClick plugin) {
        this.dataService = plugin.getDataManager().getFarmWorldDataService();
        this.finder = new FarmWorldFinder(dataService);

        registerWorlds();
    }


    /**
     * Registers all the non-registered {@link World worlds} as {@link FarmWorld farm worlds}.
     */
    private void registerWorlds() {
        Bukkit.getWorlds().forEach(world -> dataService.insertOne(FarmWorld.fromWorld(world)));
    }


    /**
     * Gets all the {@link FarmWorld farm worlds}.
     *
     * @return the found farm worlds.
     */
    @NotNull
    public CompletableFuture<List<FarmWorld>> getWorlds() {
        return dataService.getMany(0, WORLDS_MAX);
    }


    /**
     * Checks whether the provided {@link FarmWorld farm world} is accessible by {@link CropClick}.
     *
     * @param world the world to be checked.
     *
     * @return true if accessible, otherwise false.
     */
    public boolean isAccessible(FarmWorld world) {
        return world != null && !world.isBanished();
    }

}