package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class WorldDataStorage extends DataStorage {

    private @Getter HashMap<String, FarmWorld> worlds;

    private final Type type;


    public WorldDataStorage(@NotNull CropClick plugin) {
        super(plugin, "worlds.json");
        this.type = new TypeToken<HashMap<String, FarmWorld>>() {}.getType();
        this.worlds = new HashMap<>();
    }


    /**
     * Handles the fetching and loading of the worlds.
     */
    @Override
    public void fetchData() {
        super.fetchData();
        loadWorlds();
    }


    /**
     * Handles the saving of the worlds.
     */
    @Override
    public void saveData() {
        saveWorlds();
    }


    /**
     * If the world is already registered, return. Otherwise, register the world.
     *
     * @param world The world to register.
     */
    public void registerWorld(@NotNull FarmWorld world) {
        if (worlds.containsKey(world.getName())) {
            return;
        }
        worlds.put(world.getName(), world);
    }


    /**
     * Remove the world from the list of worlds.
     *
     * @param world The world to unregister.
     */
    @SuppressWarnings("unused")
    public void unregisterWorld(@NotNull FarmWorld world) {
        worlds.remove(world.getName());
    }


    /**
     * It loads the worlds from the file.
     */
    private void loadWorlds() {
        HashMap<String, FarmWorld> loaded = gson.fromJson(fileData, new TypeToken<HashMap<String, FarmWorld>>() {}.getType());
        this.worlds = loaded != null ? loaded : new HashMap<>();
    }


    /**
     * It converts the HashMap of worlds into a JSON object, then saves it to the file.
     */
    private void saveWorlds() {
        String data = gson.toJson(worlds, type);
        JsonElement dataAsJson = jsonParser.parse(data);
        fileData = dataAsJson.getAsJsonObject();

        super.saveData();
    }


    /**
     * Returns the FarmWorld object with the given name, or null if no such world exists.
     *
     * @param name The name of the world you want to find.
     *
     * @return A FarmWorld object or null.
     */
    public @Nullable FarmWorld findWorldByName(@NotNull String name) {
        return worlds.getOrDefault(name, null);
    }


    /**
     * Returns the FarmWorld object associated with the given World object, or null if no such FarmWorld exists.
     *
     * @param world The world to find the FarmWorld for.
     *
     * @return A FarmWorld object or null.
     */
    public @Nullable FarmWorld findWorldByWorld(@NotNull World world) {
        return worlds.getOrDefault(world.getName(), null);
    }


    /**
     * Returns the FarmWorld object associated with the given player, or null if the player is not in a FarmWorld.
     *
     * @param player The player to find the world for.
     *
     * @return A FarmWorld object or null.
     */
    public @Nullable FarmWorld findWorldByPlayer(@NotNull Player player) {
        return worlds.getOrDefault(player.getWorld().getName(), null);
    }

}