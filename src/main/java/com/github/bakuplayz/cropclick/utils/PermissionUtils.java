package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.commands.CommandManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtBlockListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerLinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUnlinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUpdateAutofarmListener;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


/**
 * A utility class for {@link Permission permissions}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PermissionUtils {

    /**
     * Checks whether the {@link Player provided player} can execute the '/crop' command.
     *
     * @param player the player to check.
     *
     * @return true if it can, otherwise false.
     *
     * @see CommandManager
     */
    public static boolean canPlayerExecuteGeneralCommand(@NotNull Player player) {
        return player.hasPermission("cropclick.command.general");
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to link a {@link Autofarm autofarm}.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     *
     * @see PlayerLinkAutofarmListener
     */
    public static boolean canLinkFarm(@NotNull Player player) {
        return player.hasPermission("cropclick.autofarm.link");
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to unlink a {@link Autofarm autofarm}.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     *
     * @see PlayerUnlinkAutofarmListener
     */
    public static boolean canUnlinkFarm(@NotNull Player player) {
        return player.hasPermission("cropclick.autofarm.unlink");
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to unlink others {@link Autofarm autofarms}.
     *
     * @param player   the player to check.
     * @param autofarm the autofarm to check.
     *
     * @return true if allowed, otherwise false.
     *
     * @see PlayerUnlinkAutofarmListener
     */
    public static boolean canUnlinkOthersFarm(@NotNull Player player, @NotNull Autofarm autofarm) {
        if (!AutofarmUtils.isOwner(player, autofarm)) {
            return player.hasPermission("cropclick.autofarm.unlink.others");
        }
        return canUnlinkFarm(player);
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to update a {@link Autofarm autofarm}.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     *
     * @see PlayerUpdateAutofarmListener
     */
    public static boolean canUpdateFarm(@NotNull Player player) {
        return player.hasPermission("cropclick.autofarm.update");
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to update others {@link Autofarm autofarms}.
     *
     * @param player the player to check.
     * @param other  the owner of the autofarm.
     *
     * @return true if allowed, otherwise false.
     *
     * @see PlayerUpdateAutofarmListener
     */
    public static boolean canUpdateOthersFarm(@NotNull Player player, @NotNull UUID other) {
        if (!player.getUniqueId().equals(other)) {
            return player.hasPermission("cropclick.autofarm.update.others");
        }
        return canUpdateFarm(player);
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to interact at a {@link Autofarm autofarm}.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     *
     * @see PlayerInteractAtBlockListener
     */
    public static boolean canInteractAtFarm(@NotNull Player player) {
        return player.hasPermission("cropclick.autofarm.interact");
    }


    /**
     * "Checks whether the {@link Player provided player} is allowed to interact with others {@link Autofarm autofarms}."
     * <p>
     * Additional check: If the {@link Autofarm autofarm} has an {@link Autofarm#UNKNOWN_OWNER unknown owner}, then check if the player can claim the farm.
     * </p>
     *
     * @param player   the player to check.
     * @param autofarm the autofarm to interact with.
     *
     * @return true if allowed, otherwise false.
     *
     * @see PlayerInteractAtBlockListener
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean canInteractAtOthersFarm(@NotNull Player player, @NotNull Autofarm autofarm) {
        UUID other = player.getUniqueId();

        if (other.equals(Autofarm.UNKNOWN_OWNER)) {
            return player.hasPermission("cropclick.autofarm.claim");
        }

        if (!other.equals(autofarm.getOwnerID())) {
            return player.hasPermission("cropclick.autofarm.interact.others");
        }

        return canInteractAtFarm(player);
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to destroy a {@link Crop crop}.
     *
     * @param player the player to check.
     * @param name   the name of the crop.
     *
     * @return true if allowed, otherwise false.
     */
    public static boolean canDestroyCrop(@NotNull Player player, @NotNull String name) {
        return player.hasPermission("cropclick.destroy." + name);
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to plant a {@link Crop crop}.
     *
     * @param player the player to check.
     * @param name   the name of the crop.
     *
     * @return true if allowed, otherwise false.
     */
    public static boolean canPlantCrop(@NotNull Player player, @NotNull String name) {
        return player.hasPermission("cropclick.plant." + name);
    }


    /**
     * Checks whether the {@link Player provided player} is allowed to harvest a {@link Crop crop}.
     *
     * @param player the player to check.
     * @param name   the name of the crop.
     *
     * @return true if allowed, otherwise false.
     */
    public static boolean canHarvestCrop(@NotNull Player player, @NotNull String name) {
        return player.hasPermission("cropclick.harvest." + name);
    }

}