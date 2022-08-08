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

    public static boolean canLinkFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.link");
    }


    public static boolean canUnlinkFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.unlink");
    }


    public static boolean canUpdateFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.update");
    }


    public static boolean canInteractAtFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.interact");
    }


    public static boolean canDestroyCrop(@NotNull Player player, String name) {
        return player.isOp() || player.hasPermission("cropclick.destroy." + name);
    }


    public static boolean canPlantCrop(@NotNull Player player, String name) {
        return player.isOp() || player.hasPermission("cropclick.plant." + name);
    }


    public static boolean canHarvestCrop(@NotNull Player player, String name) {
        return player.isOp() || player.hasPermission("cropclick.harvest." + name);
    }

}