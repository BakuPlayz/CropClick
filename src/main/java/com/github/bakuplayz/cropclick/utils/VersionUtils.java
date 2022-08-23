package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class VersionUtils {

    /**
     * It gets the server version.
     *
     * @return The version of the server.
     */
    public static @NotNull String getServerVersion() {
        return Bukkit.getBukkitVersion().split("-")[0].substring(2);
    }


    /**
     * Returns true if the current server version is between or equal to the given min and max versions.
     *
     * @param minVersion The minimum version of the server that the plugin is compatible with.
     * @param maxVersion The maximum version of the server that this plugin is compatible with.
     *
     * @return A boolean value.
     */
    public static boolean between(double minVersion, double maxVersion) {
        double serverVersion = Double.parseDouble(VersionUtils.getServerVersion());
        return (serverVersion >= minVersion) && (maxVersion >= serverVersion);
    }


    /**
     * If the server version supports bamboos, return true.
     *
     * @return A boolean value.
     */
    public static boolean supportsBamboos() {
        return !between(0.0, 13.9);
    }


    /**
     * If the server version supports sweet berries, return true.
     *
     * @return A boolean value.
     */
    public static boolean supportsSweetBerries() {
        return !between(0.0, 13.9);
    }


    /**
     * If the server version supports twisting vines, return true.
     *
     * @return A boolean value.
     */
    public static boolean supportsTwistingVines() {
        return !between(0.0, 15.9);
    }


    /**
     * If the server version supports glow berries, return true.
     *
     * @return A boolean value.
     */
    public static boolean supportsGlowBerries() {
        return !between(0.0, 16.9);
    }


    /**
     * If the server version supports dripleaves, return true.
     *
     * @return A boolean value.
     */
    public static boolean supportsDripleaves() {
        return !between(0.0, 16.9);
    }

}