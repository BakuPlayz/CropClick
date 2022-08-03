package com.github.bakuplayz.cropclick.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PermissionUtils {

    public static boolean canLink(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.link");
    }


    public static boolean canUnlink(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.unlink");
    }


    public static boolean canInteractAtFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.interact");
    }


    public static boolean canDestroyCrop(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.crop.destroy");
    }


    public static boolean canPlaceCrop(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.crop.place");
    }


    public static boolean canHarvestCrop(@NotNull Player player, String name) {
        return player.isOp() || player.hasPermission("cropclick.crop.harvest." + name);
    }

}