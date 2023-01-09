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

package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * A class representing {@link FarmWorld FarmWorlds} as a JSON file.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class WorldDataStorage extends DataStorage {

    private @Getter HashMap<String, FarmWorld> worlds;

    /**
     * A variable used to save the {@link #worlds hashmap of worlds} appropriately, when using GSON.
     */
    private final Type type;


    public WorldDataStorage(@NotNull CropClick plugin) {
        super(plugin, "worlds.json");
        this.type = new TypeToken<HashMap<String, FarmWorld>>() {}.getType();
        this.worlds = new HashMap<>();
    }


    /**
     * Fetches the {@link #fileData file data}.
     */
    @Override
    public void fetchData() {
        super.fetchData();
        loadWorlds();
    }


    /**
     * Saves the {@link #worlds}.
     */
    @Override
    public void saveData() {
        saveWorlds();
    }


    /**
     * Register the provided {@link FarmWorld world}.
     *
     * @param world the world to register.
     */
    public void registerWorld(@NotNull FarmWorld world) {
        if (worlds.containsKey(world.getName())) {
            return;
        }
        worlds.put(world.getName(), world);
    }


    /**
     * Unregister the provided {@link FarmWorld world}.
     *
     * @param world the world to unregister.
     */
    @SuppressWarnings("unused")
    public void unregisterWorld(@NotNull FarmWorld world) {
        worlds.remove(world.getName());
    }


    /**
     * Loads all the {@link #worlds}.
     */
    private void loadWorlds() {
        HashMap<String, FarmWorld> loaded = gson.fromJson(fileData, type);
        this.worlds = loaded != null ? loaded : new HashMap<>();
    }


    /**
     * Saves all the {@link #worlds}.
     */
    private void saveWorlds() {
        try {
            String data = gson.toJson(worlds, type);
            JsonElement dataAsJson = jsonParser.parse(data);
            fileData = dataAsJson.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_OTHER.send(plugin.getLogger(), fileName);
        }

        super.saveData();
    }


    /**
     * Finds the {@link FarmWorld farm world} based on the provided name.
     *
     * @param name the name to base the findings on.
     *
     * @return the found farm world, otherwise null.
     */
    public @Nullable FarmWorld findWorldByName(@NotNull String name) {
        return worlds.getOrDefault(name, null);
    }


    /**
     * Finds the {@link FarmWorld farm world} based on the {@link World world}.
     *
     * @param world the world to base the findings on.
     *
     * @return the found farm world, otherwise null.
     */
    public @Nullable FarmWorld findWorldByWorld(@NotNull World world) {
        return worlds.getOrDefault(world.getName(), null);
    }


    /**
     * Finds the {@link FarmWorld farm world} based on the {@link Location player's location}.
     *
     * @param player the player to base the findings on.
     *
     * @return the found farm world, otherwise null.
     */
    public @Nullable FarmWorld findWorldByPlayer(@NotNull Player player) {
        return worlds.getOrDefault(player.getWorld().getName(), null);
    }

}