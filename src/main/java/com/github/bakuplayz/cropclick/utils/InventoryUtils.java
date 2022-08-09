package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class InventoryUtils {

    public static boolean canContain(@NotNull Inventory inventory, @NotNull ItemStack[] items) {
        Inventory clone = InventoryUtils.cloneInventory(inventory);
        HashMap<Integer, ItemStack> leftOver = clone.addItem(items);

        return !leftOver.isEmpty();
    }


    /**
     * It creates a new inventory with the same size as the given inventory, and then sets the contents of the new
     * inventory to the contents of the given inventory.
     *
     * @param inventory The inventory to clone.
     * @param holder    The holder of the inventory.
     *
     * @return An Inventory object
     */
    public static @NotNull Inventory cloneInventory(@NotNull Inventory inventory, @NotNull InventoryHolder holder) {
        Inventory clone = Bukkit.createInventory(holder, inventory.getSize());
        clone.setContents(inventory.getContents());
        return clone;
    }


    /**
     * It creates a new inventory with the same size as the given inventory, and then sets the contents of the new
     * inventory to the contents of the given inventory.
     *
     * @param inventory The inventory to clone.
     *
     * @return A clone of the inventory.
     */
    public static @NotNull Inventory cloneInventory(@NotNull Inventory inventory) {
        Inventory clone = Bukkit.createInventory(null, inventory.getSize());
        clone.setContents(inventory.getContents());
        return clone;
    }

}