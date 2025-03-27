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

import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.container.DataContainer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_OTHER;

public final class WorldDataContainer extends DataContainer<HashMap<String, FarmWorld>> implements LoggerContext {

    public WorldDataContainer() {
        super("worlds.json");
        setHandler(new Handler());
    }


    /**
     * Finds the {@link FarmWorld farm world} based on the provided name.
     *
     * @param name the name to base the findings on.
     *
     * @return the found farm world, otherwise null.
     */
    @Nullable
    public FarmWorld findWorldByName(@NotNull String name) {
        return getData().getOrDefault(name, null);
    }


    /**
     * Finds the {@link FarmWorld farm world} based on the {@link World world}.
     *
     * @param world the world to base the findings on.
     *
     * @return the found farm world, otherwise null.
     */
    @Nullable
    public FarmWorld findWorldByWorld(@NotNull World world) {
        return getData().getOrDefault(world.getName(), null);
    }


    /**
     * Finds the {@link FarmWorld farm world} based on the {@link Location player's location}.
     *
     * @param player the player to base the findings on.
     *
     * @return the found farm world, otherwise null.
     */
    @Nullable
    public FarmWorld findWorldByPlayer(@NotNull Player player) {
        return getData().getOrDefault(player.getWorld().getName(), null);
    }


    private final class Handler implements EventHandler {

        @Override
        public void onCreateSuccess() {

        }


        @Override
        public void onCreateFailure() {

        }


        @Override
        public void onReloadSuccess() {

        }


        @Override
        public void onReloadFailure() {

        }


        @Override
        public void onSaveSuccess() {

        }


        @Override
        public void onSaveFailure() {
            DATA_STORAGE_FAILED_SAVE_OTHER.send(getLogger(), fileName);
        }


        @Override
        public void onResetSuccess() {

        }


        @Override
        public void onResetFailure() {

        }

    }

}
