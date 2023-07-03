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

package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.crop.crops.ground.PitcherPlant;
import com.github.bakuplayz.cropclick.crop.crops.ground.SweetBerries;
import com.github.bakuplayz.cropclick.crop.crops.ground.Torchflower;
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


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link PitcherPlant pitcher plants}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsPitcherPlants() {
        return !between(0.0, 20);
    }


    /**
     * Checks whether the {@link #getServerVersion() server version} supports {@link Torchflower torchflowers}.
     *
     * @return true if it does, otherwise false.
     */
    public static boolean supportsTorchFlowers() {
        return !between(0.0, 20);
    }

}