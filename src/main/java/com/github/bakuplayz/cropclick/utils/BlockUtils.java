package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
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
        return block.getType() == Material.SAND || block.getType() == Material.SOIL;
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