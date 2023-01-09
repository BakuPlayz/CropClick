/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;


/**
 * A utility class for {@link Inventory inventories}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class InventoryUtils {

    /**
     * Checks whether the {@link Inventory provided inventory} can contain the {@link ItemStack provided item}.
     *
     * @param inventory the inventory to check.
     * @param item      the item to add.
     *
     * @return true if it could, otherwise false.
     */
    public static boolean canContain(@NotNull Inventory inventory, @NotNull ItemStack item) {
        Inventory temporary = cloneInventory(inventory);

        if (inventory instanceof PlayerInventory && inventory.getSize() < 36) {
            temporary.setItem(36, new ItemStack(Material.STONE, 64));
            temporary.setItem(37, new ItemStack(Material.STONE, 64));
            temporary.setItem(38, new ItemStack(Material.STONE, 64));
            temporary.setItem(39, new ItemStack(Material.STONE, 64));
            temporary.setItem(40, new ItemStack(Material.STONE, 64));
        }

        return temporary.addItem(item).isEmpty();
    }


    /**
     * Clones the {@link Inventory passed inventory}.
     *
     * @param inventory the inventory to clone.
     *
     * @return the cloned inventory.
     */
    private static @NotNull Inventory cloneInventory(@NotNull Inventory inventory) {
        Inventory temporary = inventory instanceof DoubleChestInventory
                              ? Bukkit.createInventory(null, 54)
                              : Bukkit.createInventory(null, inventory.getType());

        for (int i = 0; i < temporary.getSize(); ++i) {
            temporary.setItem(i, inventory.getItem(i));
        }

        return temporary;
    }

}