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
import com.github.bakuplayz.cropclick.datastorages.datastorage.WorldDataStorage;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;


/**
 * A manager holding and controlling all the {@link #worldData world data} and {@link #worlds farm worlds}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class WorldManager {

    private final @NotNull WorldDataStorage worldData;

    /**
     * A variable containing all the registered {@link World worlds} as {@link FarmWorld farm worlds}.
     */
    private final @Getter HashMap<String, FarmWorld> worlds;


    public WorldManager(@NotNull CropClick plugin) {
        this.worldData = plugin.getWorldData();
        this.worlds = worldData.getWorlds();
    }


    /**
     * Registers all the non-registered {@link World worlds} as {@link FarmWorld farm worlds}.
     */
    public void registerWorlds() {
        Bukkit.getWorlds().forEach(world -> worldData.registerWorld(new FarmWorld(world)));
    }


    /**
     * Finds a {@link FarmWorld farm world} based on the {@link World world}.
     *
     * @param world the world to base the findings on.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    public @Nullable FarmWorld findByWorld(@NotNull World world) {
        return worldData.findWorldByWorld(world);
    }


    /**
     * Finds a {@link FarmWorld farm world} based on its name.
     *
     * @param name the name of the world to find.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    public @Nullable FarmWorld findByName(@NotNull String name) {
        return worldData.findWorldByName(name);
    }


    /**
     * Finds a {@link FarmWorld farm world} based on the {@link Player player's position}.
     *
     * @param player the player to base the findings on.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    public @Nullable FarmWorld findByPlayer(@NotNull Player player) {
        return worldData.findWorldByPlayer(player);
    }


    /**
     * Checks whether the provided {@link FarmWorld farm world} is accessible by {@link CropClick}.
     *
     * @param farmWorld the world to be checked.
     *
     * @return true if accessible, otherwise false.
     */
    public boolean isAccessible(FarmWorld farmWorld) {
        return farmWorld != null && !farmWorld.isBanished();
    }

}