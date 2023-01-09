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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


/**
 * A utility class for {@link Block blocks}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class BlockUtils {

    /**
     * Checks whether the {@link Block passed block} is {@link Material#AIR air}.
     *
     * @param block the block to check.
     *
     * @return true if air, otherwise false.
     */
    public static boolean isAir(Block block) {
        return block == null || block.getType() == Material.AIR;
    }


    /**
     * Checks whether the {@link Block provided block} is a double chest.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isDoubleChest(@NotNull Block block) {
        BlockState blockState = block.getState();
        if (!(blockState instanceof Chest)) {
            return false;
        }

        Chest chest = (Chest) blockState;
        Inventory inventory = chest.getInventory();
        return inventory instanceof DoubleChestInventory;
    }


    /**
     * Checks whether two different {@link Block blocks} are of the {@link Material same type}.
     *
     * @param first  the first block to check.
     * @param second the second block to check.
     *
     * @return true if they are the same, otherwise false.
     */
    public static boolean isSameType(@NotNull Block first, @NotNull Block second) {
        return first.getType() == second.getType();
    }


    /**
     * Checks whether the {@link Block provided block} matches the {@link Material provided type}.
     *
     * @param block the block to check.
     * @param type  the type to match.
     *
     * @return true if they are the same, otherwise false.
     */
    public static boolean isSameType(@NotNull Block block, @NotNull Material type) {
        return block.getType() == type;
    }


    /**
     * Checks whether the {@link Block provided block} matches any of the {@link Material passed materials}.
     *
     * @param block the block to check.
     * @param types the types to match.
     *
     * @return true if it matches any, otherwise false.
     */
    @SuppressWarnings("unused")
    public static boolean isAnyType(@NotNull Block block, @NotNull Material @NotNull ... types) {
        return Arrays.stream(types).anyMatch(type -> type == block.getType());
    }

}