package com.github.bakuplayz.cropclick.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class MessageUtil {

    @Contract("_ -> new")
    public static @NotNull String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}