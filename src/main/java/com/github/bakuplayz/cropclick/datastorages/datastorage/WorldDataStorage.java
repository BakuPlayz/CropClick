package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class WorldDataStorage extends DataStorage {

    private @Getter HashMap<String, FarmWorld> worlds = new HashMap<>();

    public WorldDataStorage(@NotNull CropClick plugin) {
        super("worlds.json", plugin);

        loadWorlds();
    }

    public void addWorld(@NotNull FarmWorld world) {
        worlds.put(world.getName(), world);
    }

    public void removeWorld(@NotNull FarmWorld world) {
        worlds.remove(world.getName());
    }

    private void loadWorlds() {
        HashMap<String, FarmWorld> loaded = gson.fromJson(fileData, (Type) FarmWorld.class);
        this.worlds = loaded != null ? loaded : new HashMap<>();
    }

    public void saveWorlds() {
        try {
            String data = gson.toJson(worlds);
            JsonElement dataAsJson = JsonParser.parseString(data);
            fileData = dataAsJson.getAsJsonObject();

            saveData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public @Nullable FarmWorld findWorldByName(@NotNull String name) {
        return worlds.getOrDefault(name, null);
    }

    public @Nullable FarmWorld findWorldByWorld(@NotNull World world) {
        return worlds.getOrDefault(world.getName(), null);
    }

    public @Nullable FarmWorld findWorldByPlayer(@NotNull Player player) {
        return worlds.getOrDefault(player.getWorld().getName(), null);
    }

    public @NotNull List<FarmWorld> getWorlds(int startIndex, int size) {
        Preconditions.checkArgument(startIndex < 0, "The startIndex cannot be less than zero.");
        Preconditions.checkArgument(size <= 0, "The size of retrieved farms cannot be less or equal to zero.");

        return worlds.values().stream()
                .skip(startIndex)
                .limit(size)
                .collect(Collectors.toList());
    }

}