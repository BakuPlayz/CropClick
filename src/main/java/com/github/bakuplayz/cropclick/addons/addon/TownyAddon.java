package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
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
 * @since 1.6.0
 */
public final class TownyAddon extends Addon {


    public TownyAddon(@NotNull CropClick plugin) {
        super(plugin, "Towny");
    }


    /**
     * Returns true if the player can destroy a block (in this case: just a dummy stone).
     *
     * @param player The player who is trying to destroy the crop.
     * @return A boolean value.
     */
    public boolean canDestroyCrop(@NotNull Player player) {
        PlayerCache cache = Towny.getPlugin().getCache(player);
        return cache.getDestroyPermission(Material.STONE);
    }

}