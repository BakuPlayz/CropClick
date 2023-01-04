package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.crop.crops.ground.SweetBerries;
import com.github.bakuplayz.cropclick.crop.crops.roof.GlowBerries;
import com.github.bakuplayz.cropclick.crop.crops.tall.Bamboo;
import com.github.bakuplayz.cropclick.crop.crops.tall.Dripleaf;
import com.github.bakuplayz.cropclick.crop.crops.tall.TwistingVines;
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
    public static boolean between(double min, double max) {
        double serverVersion = Double.parseDouble(VersionUtils.getServerVersion());
        return (serverVersion >= min) && (max >= serverVersion);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link Bamboo bamboos}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsBamboos() {
        return !between(0.0, 13.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link SweetBerries sweet berries}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsSweetBerries() {
        return !between(0.0, 13.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link TwistingVines twisting vines}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsTwistingVines() {
        return !between(0.0, 15.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link GlowBerries glow berries}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsGlowBerries() {
        return !between(0.0, 16.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link Dripleaf dripleaves}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsDripleaves() {
        return !between(0.0, 16.9);
    }

}