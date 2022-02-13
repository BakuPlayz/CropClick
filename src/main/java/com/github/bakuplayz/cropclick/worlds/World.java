package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import lombok.Getter;

import java.util.List;

public final class World {

    private @Getter String name;
    
    private @Getter boolean isBanished;
    private @Getter boolean allowsAutofarm; // better naming
    private @Getter boolean allowsPlayerClick; // better naming

    private @Getter List<Addon> allowedAddons;

    public World(org.bukkit.World world) {

    }
}
