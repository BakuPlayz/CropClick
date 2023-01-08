package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.autofarm.container.ContainerType;
import com.github.bakuplayz.cropclick.crop.crops.ground.Beetroot;
import com.github.bakuplayz.cropclick.crop.crops.tall.Chorus;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;


/**
 * A utility class for version specificity.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class VersionUtils {

    /**
     * Gets the server version.
     *
     * @return the version of the server.
     */
    public static @NotNull String getServerVersion() {
        return Bukkit.getBukkitVersion().split("-")[0].substring(2);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} is within or equal the min and max versions.
     *
     * @param min the minimum version.
     * @param max the maximum version.
     *
     * @return true if between the interval, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean between(double min, double max) {
        double serverVersion = Double.parseDouble(VersionUtils.getServerVersion());
        return (serverVersion >= min) && (max >= serverVersion);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link Beetroot beetroots}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsBeetroots() {
        return !between(8, 8.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link Chorus chorus}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsChorus() {
        return !between(8, 8.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link ContainerType#SHULKER shulkers}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsShulkers() {
        return !between(8, 10.9);
    }

}