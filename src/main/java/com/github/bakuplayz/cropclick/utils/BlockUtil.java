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
 */
public final class BlockUtil {

    public static boolean isAir(Block block) {
        return block == null || block.getType() == Material.AIR;
    }

    public static boolean isSameType(@NotNull Block b1, @NotNull Block b2) {
        return b1.getType() == b2.getType();
    }

    public static boolean isSameType(@NotNull Block b, @NotNull Material m) {
        return b.getType() == m;
    }

    public static boolean isAnyType(@NotNull Block b, @NotNull Material... types) {
        return Arrays.stream(types).anyMatch(type -> type == b.getType());
    }

}