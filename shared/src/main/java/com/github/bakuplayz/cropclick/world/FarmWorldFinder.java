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
package com.github.bakuplayz.cropclick.world;

import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.datacontainers.services.world.FarmWorldDataService;
import lombok.AllArgsConstructor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public final class FarmWorldFinder {

    @NotNull
    private final FarmWorldDataService service;


    /**
     * Finds the {@link FarmWorld} based on the provided world.
     *
     * @param world the id to base the findings on.
     *
     * @return the found FarmWorld, otherwise CompletableFuture of null.
     */
    @NotNull
    public CompletableFuture<FarmWorld> findByWorld(@NotNull World world) {
        return service.getOne(world.getName());
    }


    /**
     * Finds the {@link FarmWorld} based on the provided world name.
     *
     * @param name the name to base the findings on.
     *
     * @return the found FarmWorld, otherwise null.
     */
    @NotNull
    public CompletableFuture<FarmWorld> findByName(String name) {
        return name == null ? CompletableFuture.completedFuture(null) : service.getOne(name);
    }


    /**
     * Finds a {@link FarmWorld farm world} based on the {@link Player player's position}.
     *
     * @param player the player to base the findings on.
     *
     * @return the found {@link FarmWorld}, otherwise CompletableFuture of null.
     */
    @NotNull
    public CompletableFuture<FarmWorld> findByPlayer(@NotNull CropPlayer player) {
        return findByWorld(player.getOfflinePlayer().getPlayer().getWorld());
    }

}
