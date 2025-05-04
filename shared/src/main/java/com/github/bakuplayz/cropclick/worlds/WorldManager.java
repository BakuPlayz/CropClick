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

package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.FarmWorldAPI;
import com.github.bakuplayz.cropclick.datacontainers.services.world.FarmWorldDataService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * A manager holding and controlling all the {@link FarmWorld farm worlds}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 3.0.0
 */
public final class WorldManager implements FarmWorldAPI {

    private final FarmWorldDataService dataService;

    private final FarmWorldFinder worldFinder;


    public WorldManager(@NotNull CropClick plugin) {
        this.dataService = plugin.getDataManager().getFarmWorldDataService();
        this.worldFinder = new FarmWorldFinder(dataService);
    }


    /**
     * Gets all the {@link FarmWorld farm worlds}.
     *
     * @return the found farm worlds.
     */
    @NotNull
    public List<FarmWorld> getWorlds() {
        return new ArrayList<>(dataService.getMany(0, 10000).join());
    }


    public int getAmountOfWorlds() {
        return dataService.countAll().join();
    }


    /**
     * Registers all the non-registered {@link World worlds} as {@link FarmWorld farm worlds}.
     */
    public void registerWorlds() {
        Bukkit.getWorlds().forEach(world -> dataService.insertOne(new FarmWorld(world)));
    }


    /**
     * Finds a {@link FarmWorld farm world} based on the {@link World world}.
     *
     * @param world the world to base the findings on.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    @Nullable
    public FarmWorld findByWorld(@NotNull World world) {
        return worldFinder.findByWorld(world);
    }


    /**
     * Finds a {@link FarmWorld farm world} based on its name.
     *
     * @param name the name of the world to find.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    @Nullable
    public FarmWorld findByName(@NotNull String name) {
        return worldFinder.findByName(name);
    }


    /**
     * Finds a {@link FarmWorld farm world} based on the {@link Player player's position}.
     *
     * @param player the player to base the findings on.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    @Nullable
    public FarmWorld findByPlayer(@NotNull Player player) {
        return worldFinder.findByWorld(player.getWorld());
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