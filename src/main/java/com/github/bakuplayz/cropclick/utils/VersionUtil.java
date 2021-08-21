package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class VersionUtil {

    public static @NotNull String getServerVersion() {
        return Bukkit.getServer().getBukkitVersion().split("-")[0].substring(2);
    }

    public static boolean isInInterval(final double start, final double end) {
        double serverVersion = Double.parseDouble(getServerVersion());
        return (serverVersion >= start) && (end >= serverVersion);
    }

    public static boolean supportsShulkers() {
        return isInInterval(11, 100);
    }
}
