package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.location.DoublyLocation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class BlockUtils {

    /**
     * If the block is null or the block's type is air, return true.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public static boolean isAir(Block block) {
        return block == null || block.getType() == Material.AIR;
    }


    /**
     * Returns true if the given block is a plantable surface, false otherwise.
     *
     * @param block The block to check.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isPlantableSurface(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.SAND, Material.SOIL); //TODO: Material.ENDER_STONE for chorus
    }


    /**
     * If the block is a chest, and the inventory of the chest is a double chest inventory, then the block is a double
     * chest.
     *
     * @param block The block that you want to check if it's a double chest.
     *
     * @return A boolean value.
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
     * Returns the location of the double chest that the given block is a part of, or null if the block is not a double
     * chest.
     *
     * @param block The block you want to get the double chest of.
     *
     * @return A DoublyLocation object.
     */
    public static @Nullable DoublyLocation getAsDoubleChest(@NotNull Block block) {
        return LocationUtils.getAsDoubly(block.getLocation());
    }


    /**
     * Returns true if the two blocks are the same type.
     *
     * @param b1 The first block to compare.
     * @param b2 The block to compare to.
     *
     * @return A boolean value.
     */
    public static boolean isSameType(@NotNull Block b1, @NotNull Block b2) {
        return b1.getType() == b2.getType();
    }


    /**
     * Returns true if the block is of the same type as the material.
     *
     * @param b The block you want to check
     * @param m The material to check against
     *
     * @return A boolean value.
     */
    public static boolean isSameType(@NotNull Block b, @NotNull Material m) {
        return b.getType() == m;
    }


    /**
     * Returns true if the block is any of the given types.
     *
     * @param b The block to check
     *
     * @return A boolean value.
     */
    @SuppressWarnings("unused")
    public static boolean isAnyType(@NotNull Block b, @NotNull Material @NotNull ... types) {
        return Arrays.stream(types).anyMatch(type -> type == b.getType());
    }

}