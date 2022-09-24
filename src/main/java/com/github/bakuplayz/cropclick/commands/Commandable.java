package com.github.bakuplayz.cropclick.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Commandable {

    @NotNull String getDescription();

    @NotNull String getUsage();

    @NotNull String getPermission();

    boolean hasPermission(@NotNull Player player);

    void perform(@NotNull Player player, String[] args);

}