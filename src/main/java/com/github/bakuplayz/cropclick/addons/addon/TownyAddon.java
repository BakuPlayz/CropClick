package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.PlayerCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class TownyAddon extends Addon {

    public TownyAddon(@NotNull AddonsConfig config) {
        super("Towny", config);
    }

    public boolean canDestroyCrop(@NotNull Player player) {
        PlayerCache cache = Towny.getPlugin().getCache(player);
        return cache.getDestroyPermission(Material.STONE);
    }

}
