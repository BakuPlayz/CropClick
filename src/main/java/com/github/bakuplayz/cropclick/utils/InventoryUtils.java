package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class InventoryUtils {
    
    /**
     * Create a temporary inventory, add all the items from the original inventory to it, then add the item to drop to it,
     * and if the result is empty, then the item fits.
     *
     * @param inventory The inventory to check if the item fits in.
     * @param drop      The itemstack you want to check if it fits in the inventory.
     *
     * @return A boolean value.
     */
    public static boolean canFit(@NotNull Inventory inventory, @NotNull ItemStack drop) {
        Inventory temporary = Bukkit.createInventory(null, inventory.getSize());

        for (int i = 0; i < inventory.getSize(); i++) {
            temporary.setItem(i, inventory.getItem(i));
        }

        return temporary.addItem(drop).isEmpty();
    }

}