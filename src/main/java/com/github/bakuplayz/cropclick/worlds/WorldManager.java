package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datastorages.datastorage.WorldDataStorage;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class WorldManager {

    private final @NotNull CropClick plugin;

    private final @NotNull WorldDataStorage worldData;

    public WorldManager(@NotNull CropClick plugin) {
        this.worldData = plugin.getWorldDataStorage();
        this.plugin = plugin;

        registerWorlds();
    }

    private void registerWorlds() {
        Bukkit.getWorlds().forEach(world -> worldData.addWorld(new World(plugin, world)));
    }

    public World findByWorld(@NotNull org.bukkit.World world) {
        return findByName(world.getName());
    }

    public World findByName(@NotNull String name) {
        return worldData.getWorlds().stream()
                .filter(w -> w.getName().equals(name))
                .findFirst().orElse(null);
    }
}