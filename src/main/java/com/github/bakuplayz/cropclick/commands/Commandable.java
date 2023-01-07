package com.github.bakuplayz.cropclick.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * An interface representing commandable commands.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Commandable {

    /**
     * Gets the implementing command's description of the command.
     *
     * @return the command's description.
     */
    @NotNull String getDescription();

    /**
     * Gets the implementing command's usage of the command.
     *
     * @return the command's usage.
     */
    @NotNull String getUsage();

    /**
     * Gets the implementing command's permission of the command.
     *
     * @return the command's permission.
     */
    @NotNull String getPermission();

    /**
     * Checks whether the provided player has permission to perform the implementing command.
     *
     * @param player the player to check.
     *
     * @return true if it has, otherwise false.
     */
    boolean hasPermission(@NotNull Player player);

    /**
     * Performs the implementing command.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    void perform(@NotNull Player player, String[] args);

}