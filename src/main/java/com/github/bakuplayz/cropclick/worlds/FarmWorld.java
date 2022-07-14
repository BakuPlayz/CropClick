package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class FarmWorld {

    private final @Getter String name;

    private @Setter @Getter @Accessors(fluent = true) boolean isBanished;
    private @Setter @Getter @Accessors(fluent = true) boolean allowsPlayers;
    private @Setter @Getter @Accessors(fluent = true) boolean allowsAutofarms;

    private final @Getter List<Addon> allowedAddons = new ArrayList<>();

    public FarmWorld(@NotNull World world) {
        this.name = world.getName();
    }

    public boolean allowsAddons() {
        return !allowedAddons.isEmpty();
    }

    public boolean allowsAddon(@NotNull Addon addon) {
        return allowedAddons.contains(addon);
    }

    public void allowAddon(@NotNull Addon addon) { allowedAddons.add(addon);}

}