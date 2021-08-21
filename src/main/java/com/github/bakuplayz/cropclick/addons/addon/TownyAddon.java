package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.addons.addon.template.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.PlayerCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class TownyAddon extends Addon {

    public TownyAddon(final @NotNull AddonsConfig addonsConfig) {
        super("Towny", addonsConfig);
    }

    public boolean canDestroyCrop(final @NotNull Player player) {
        PlayerCache playerCache = Towny.getPlugin().getCache(player);
        return playerCache.getDestroyPermission(Material.STONE);
    }
}
