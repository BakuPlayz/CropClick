package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.PlayerCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the <a href="https://www.spigotmc.org/resources/towny-advanced.72694/">Towny</a> addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class TownyAddon extends Addon {

    public TownyAddon(@NotNull CropClick plugin) {
        super(plugin, "Towny");
    }
    

    /**
     * Checks whether the {@link Player provided player} is allowed to destroy {@link Crop crops}.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     */
    public boolean canDestroyCrop(@NotNull Player player) {
        PlayerCache cache = Towny.getPlugin().getCache(player);
        return cache.getDestroyPermission(Material.STONE);
    }

}