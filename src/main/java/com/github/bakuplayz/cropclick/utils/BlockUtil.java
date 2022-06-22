package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

public final class BlockUtil {

    public static boolean isAir(final Block block) {
        return block == null || block.getType() == Material.AIR;
    }

    public static boolean isSameType(final Block b1, final Block b2) {
        return b1.getType() == b2.getType();
    }

}