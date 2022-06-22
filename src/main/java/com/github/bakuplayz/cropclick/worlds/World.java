package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class World {

    private final CropClick plugin;

    private final @NotNull @Getter String name;

    private @Setter @Getter boolean isBanished;
    private @Setter @Getter boolean allowsAutomatic; // better naming
    private @Setter @Getter boolean allowsInteractive; // better naming

    private @Getter List<Addon> allowedAddons;

    public World(final @NotNull CropClick plugin,
                 final @NotNull org.bukkit.World world) {
        this.name = world.getName();
        this.plugin = plugin;
    }

    public boolean allowsAddons() {
        return !allowedAddons.isEmpty();
    }

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return new Gson().toJson(this);
    }
}