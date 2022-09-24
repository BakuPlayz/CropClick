package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


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
        Inventory temporary = Bukkit.createInventory(null, inventory.getType());
        temporary.setStorageContents(inventory.getStorageContents());

        if (inventory instanceof PlayerInventory) {
            temporary.setItem(36, new ItemStack(Material.STONE, 64));
            temporary.setItem(37, new ItemStack(Material.STONE, 64));
            temporary.setItem(38, new ItemStack(Material.STONE, 64));
            temporary.setItem(39, new ItemStack(Material.STONE, 64));
            temporary.setItem(40, new ItemStack(Material.STONE, 64));
        }

        Map<Integer, ItemStack> leftOver = temporary.addItem(drop);
        
        for (ItemStack stack : leftOver.values()) {
            if (stack.getAmount() == 0) {
                return true;
            }
        }

        return leftOver.isEmpty();
    }

}