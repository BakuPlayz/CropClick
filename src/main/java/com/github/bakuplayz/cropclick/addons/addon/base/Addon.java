package com.github.bakuplayz.cropclick.addons.addon.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class Addon {

    protected final @Getter String name;

    protected transient final CropClick plugin;
    protected transient final AddonsConfig addonsConfig;

    private transient final HashMap<String, FarmWorld> worlds;


    public Addon(@NotNull CropClick plugin, @NotNull String name) {
        this.addonsConfig = plugin.getAddonsConfig();
        this.worlds = plugin.getWorldManager().getWorlds();
        this.plugin = plugin;
        this.name = name;
    }


    /**
     * Returns true if the addon is enabled, false otherwise.
     *
     * @return A boolean value.
     */
    public boolean isEnabled() {
        return addonsConfig.isEnabled(name);
    }


    /**
     * Get the amount of worlds that have this addon banished.
     *
     * @return The number of worlds that have banished this addon.
     */
    public int getAmountOfBanished() {
        return (int) worlds.values().stream()
                           .filter(farmWorld -> farmWorld.getBanishedAddons().contains(this))
                           .count();
    }

}