package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public final class InventoryUtil {

    public static boolean canContain(@NotNull Inventory inventory, @NotNull ItemStack[] items) {
        Inventory clone = InventoryUtil.cloneInventory(inventory);
        HashMap<Integer, ItemStack> leftOver = clone.addItem(items);

        return !leftOver.isEmpty();
    }

    private static @NotNull Inventory cloneInventory(@NotNull Inventory inventory) {
        Inventory clone = Bukkit.createInventory(null, inventory.getSize());
        clone.setContents(inventory.getContents());
        return clone;
    }

}