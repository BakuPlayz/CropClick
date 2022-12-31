package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.menu.Menu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 * A utility class for {@link Menu menus}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MenuUtil {

    /**
     * If the item is null or the item is air, return true.
     *
     * @param clicked The item that was clicked.
     *
     * @return The method is returning a boolean value.
     */
    public static boolean isAir(ItemStack clicked) {
        return clicked == null || clicked.getType() == Material.AIR;
    }

}