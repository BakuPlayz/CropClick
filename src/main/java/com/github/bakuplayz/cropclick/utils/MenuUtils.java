package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 * A utility class for {@link BaseMenu menus}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MenuUtils {

    /**
     * Checks whether the {@link ItemStack passed item} is {@link Material#AIR air}.
     *
     * @param item the item to check.
     *
     * @return true if air, otherwise false.
     */
    public static boolean isAir(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

}