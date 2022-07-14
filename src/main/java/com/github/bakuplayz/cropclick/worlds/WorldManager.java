package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datastorages.datastorage.WorldDataStorage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class WorldManager {

    private final @NotNull WorldDataStorage worldData;

    public WorldManager(@NotNull CropClick plugin) {
        this.worldData = plugin.getWorldData();

        registerWorlds();
    }

    private void registerWorlds() {
        Bukkit.getWorlds().forEach(world -> worldData.addWorld(new FarmWorld(world)));
    }

    public @Nullable FarmWorld findByWorld(@NotNull World world) {
        return worldData.findWorldByWorld(world);
    }

    public @Nullable FarmWorld findByName(@NotNull String name) {
        return worldData.findWorldByName(name);
    }

    public @Nullable FarmWorld findByPlayer(@NotNull Player player) {
        return worldData.findWorldByPlayer(player);
    }

    public boolean isAccessable(FarmWorld world) {
        return world != null && !world.isBanished();
    }

}