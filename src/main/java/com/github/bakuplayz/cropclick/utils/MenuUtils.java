package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class MenuUtils {

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