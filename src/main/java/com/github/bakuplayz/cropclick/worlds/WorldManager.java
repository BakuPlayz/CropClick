package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import lombok.Getter;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class WorldManager {

    private final @Getter List<String> blacklistedWorlds;

    public WorldManager(final @NotNull CropClick plugin) {
        this.blacklistedWorlds = plugin.getConfig().getStringList("blacklistedWorlds");
    }

    public boolean isWorldBlacklisted(final @NotNull World world) {
        return blacklistedWorlds.contains(world.getName());
    }

    public boolean isWorldBlacklisted(final @NotNull String worldName) {
        return blacklistedWorlds.contains(worldName);
    }
}
