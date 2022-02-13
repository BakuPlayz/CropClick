package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.worlds.World;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class WorldDataStorage extends DataStorage {

    private final List<World> worlds = new ArrayList<>();

    public WorldDataStorage(@NotNull CropClick plugin) {
        super("worlds.json", plugin);
    }

    public void addWorld(@NotNull World world) {
        if (worlds.contains(world)) return;
        worlds.add(world);
    }

    public void removeWorld(@NotNull World world) {
        worlds.remove(world);
    }

    public void saveWorlds() {
        for (World world : worlds) {
            String data = gson.toJson(world);
            JsonElement worldAsObj = parser.parse(data);
            fileData.add(world.getName(), worldAsObj.getAsJsonObject());
        }
        saveData();
    }

    public World getWorld(final @NotNull String name) {
        return gson.fromJson(fileData.get(name), World.class);
    }

    public World getWorld(final @NotNull World world) {
        return worlds.stream()
                .filter(w -> w.getName().equals(world.getName()))
                .findFirst().orElse(null);
    }

    public List<World> getWorlds(final int start, final int end) {
        Preconditions.checkArgument(end < start, "End cannot be less than start.");
        Preconditions.checkArgument(start < 0, "Start cannot be less than zero.");

        return worlds.stream()
                .skip(start).limit(end)
                .collect(Collectors.toList());
    }

    public List<World> getWorlds() {
        return worlds;
    }
}
