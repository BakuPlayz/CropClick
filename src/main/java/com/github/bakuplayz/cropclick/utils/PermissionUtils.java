package com.github.bakuplayz.cropclick.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PermissionUtils {

    /**
     * Returns true if the player is an operator or has the permission node cropclick.autofarm.link.
     *
     * @param player The player who is trying to link the farm.
     *
     * @return A boolean value.
     */
    public static boolean canLinkFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.link");
    }


    /**
     * Returns true if the player is an operator or has the permission node cropclick.autofarm.unlink.
     *
     * @param player The player who is trying to unlink the farm.
     *
     * @return A boolean value.
     */
    public static boolean canUnlinkFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.unlink");
    }


    /**
     * If the player is not the owner of the farm, then they must be an operator or have the permission
     * cropclick.autofarm.unlink.others to unlink the farm.
     *
     * @param player The player who is trying to unlink the farm.
     * @param other  The UUID of the player whose farm you want to unlink.
     *
     * @return A boolean value.
     */
    public static boolean canUnlinkOthersFarm(@NotNull Player player, @NotNull UUID other) {
        if (!player.getUniqueId().equals(other)) {
            return player.isOp() || player.hasPermission("cropclick.autofarm.unlink.others");
        }
        return canUnlinkFarm(player);
    }


    /**
     * If the player is an operator or has the permission `cropclick.autofarm.update`, return true.
     *
     * @param player The player who is trying to update the farm.
     *
     * @return A boolean value.
     */
    public static boolean canUpdateFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.update");
    }


    /**
     * If the player is not the owner of the farm, then they must be an operator or have the permission
     * `cropclick.autofarm.update.others` to update the farm.
     *
     * @param player The player who is trying to update the farm.
     * @param other  The UUID of the player whose farm you want to update.
     *
     * @return A boolean value.
     */
    public static boolean canUpdateOthersFarm(@NotNull Player player, @NotNull UUID other) {
        if (!player.getUniqueId().equals(other)) {
            return player.isOp() || player.hasPermission("cropclick.autofarm.update.others");
        }
        return canUpdateFarm(player);
    }


    /**
     * If the player is an operator or has the permission `cropclick.autofarm.interact`, return true.
     *
     * @param player The player who is trying to interact with the farm.
     *
     * @return A boolean value.
     */
    public static boolean canInteractAtFarm(@NotNull Player player) {
        return player.isOp() || player.hasPermission("cropclick.autofarm.interact");
    }


    /**
     * If the player is not the owner of the farm, then they must be an operator or have the permission
     * "cropclick.autofarm.interact.others" to interact with the farm.
     *
     * @param player The player who is trying to interact with the farm.
     * @param other  The UUID of the player who owns the farm.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean canInteractAtOthersFarm(@NotNull Player player, @NotNull UUID other) {
        if (!player.getUniqueId().equals(other)) {
            return player.isOp() || player.hasPermission("cropclick.autofarm.interact.others");
        }
        return canInteractAtFarm(player);
    }


    /**
     * If the player is an operator or has the permission "cropclick.destroy.name", return true.
     *
     * @param player The player who is trying to destroy the crop.
     * @param name   The name of the crop.
     *
     * @return A boolean value.
     */
    public static boolean canDestroyCrop(@NotNull Player player, String name) {
        return player.isOp() || player.hasPermission("cropclick.destroy." + name);
    }


    /**
     * If the player is an operator or has the permission node `cropclick.plant.name`, return true.
     *
     * @param player The player who is planting the crop.
     * @param name   The name of the crop.
     *
     * @return A boolean value.
     */
    public static boolean canPlantCrop(@NotNull Player player, String name) {
        return player.isOp() || player.hasPermission("cropclick.plant." + name);
    }


    /**
     * If the player is an operator or has the permission "cropclick.harvest." + name, return true.
     *
     * @param player The player who is trying to harvest the crop.
     * @param name   The name of the crop.
     *
     * @return A boolean value.
     */
    public static boolean canHarvestCrop(@NotNull Player player, String name) {
        return player.isOp() || player.hasPermission("cropclick.harvest." + name);
    }

}