/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.common;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;


/**
 * A utility class for version specificity.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class Versions {

    public static final String SERVER_VERSION = Bukkit.getBukkitVersion().split("-")[0].substring(2);


    /**
     * Gets the server version.
     *
     * @return the version of the server.
     */
    @NotNull
    public static String getServerVersion() {
        return SERVER_VERSION;
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
        double serverVersion = Double.parseDouble(Versions.getServerVersion());
        return (serverVersion >= min) && (max >= serverVersion);
    }


    public static boolean isLegacy() {
        return between(0, 12.9);
    }


    public static boolean hasMainHand() {
        return Versions.between(12, Double.POSITIVE_INFINITY);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports barrels.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsBarrel() {
        return !between(0, 13.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports shulker boxes.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsShulkers() {
        return !between(0, 10.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports particles.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsParticles() {
        return !between(0, 12.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports beetroots.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsBeetroots() {
        return !between(0, 8.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports chorus'.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsChorus() {
        return !between(0, 8.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports kelps.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsKelp() {
        return !between(0, 12.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports sea pickles.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsSeaPickle() {
        return !between(0, 12.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports bamboos.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsBamboos() {
        return !between(0.0, 13.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports sweet berries.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsSweetBerries() {
        return !between(0.0, 13.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports twisting vines.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsTwistingVines() {
        return !between(0.0, 15.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports glow berries.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsGlowBerries() {
        return !between(0.0, 16.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports dripleaves.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsDripleaves() {
        return !between(0.0, 16.9);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports pitcher plants.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsPitcherPlants() {
        return !between(0.0, 20);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports torch flowers.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsTorchFlowers() {
        return !between(0.0, 20);
    }

}