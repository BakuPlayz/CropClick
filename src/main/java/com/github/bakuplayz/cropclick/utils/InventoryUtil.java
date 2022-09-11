package com.github.bakuplayz.cropclick.utils;

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
public final class InventoryUtil {

    // TODO: Check this calculation...
    public static boolean willFlood(@NotNull Inventory inventory, @NotNull ItemStack stack) {
        int addedAmount = stack.getAmount();

        for (ItemStack item : inventory.getStorageContents()) {
            if (addedAmount <= 0) {
                return false;
            }

            if (item == null) {
                addedAmount -= stack.getMaxStackSize();
            }

            if (item != null && item.isSimilar(stack)) {
                addedAmount -= (stack.getAmount() - item.getAmount());
            }

            System.out.println(addedAmount);
        }

        return true;
    }

}