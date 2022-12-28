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
 * A manager holding and controlling all the {@link #worldData world data} and {@link #worlds}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class WorldManager {

    private final @NotNull WorldDataStorage worldData;

    /**
     * Contains all the registered bukkit worlds.
     */
    private final @Getter HashMap<String, FarmWorld> worlds;


    public WorldManager(@NotNull CropClick plugin) {
        this.worldData = plugin.getWorldData();
        this.worlds = worldData.getWorlds();
        registerWorlds();
    }


    /**
     * Register all the Bukkit Worlds as FarmWorlds, if they aren't already registered.
     */
    private void registerWorlds() {
        Bukkit.getWorlds().forEach(world -> worldData.registerWorld(new FarmWorld(world)));
    }


    /**
     * Find a FarmWorld by a Bukkit World.
     *
     * @param world The world to find the FarmWorld for.
     *
     * @return A FarmWorld object
     */
    public @Nullable FarmWorld findByWorld(@NotNull World world) {
        return worldData.findWorldByWorld(world);
    }


    /**
     * Find a world by name, and return it if it exists, otherwise return null.
     *
     * @param name The name of the world to find.
     *
     * @return A FarmWorld object
     */
    public @Nullable FarmWorld findByName(@NotNull String name) {
        return worldData.findWorldByName(name);
    }


    /**
     * Find the FarmWorld that the given player is in, or null if they are not in a FarmWorld.
     *
     * @param player The player to find the world for.
     *
     * @return A FarmWorld object
     */
    public @Nullable FarmWorld findByPlayer(@NotNull Player player) {
        return worldData.findWorldByPlayer(player);
    }


    /**
     * If the world is not null and the world is not banished, return true.
     *
     * @param world The FarmWorld object that is being checked.
     *
     * @return A boolean value.
     */
    public boolean isAccessible(FarmWorld world) {
        return world != null && !world.isBanished();
    }

}