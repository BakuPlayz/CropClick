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

import com.github.bakuplayz.cropclick.location.DoublyLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A utility class for {@link Location locations}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class LocationUtils {

    /**
     * Finds a {@link DoublyLocation doubly location} based on the {@link Location location}.
     *
     * @param location the location to base the doubly on.
     *
     * @return the found {@link DoublyLocation}, otherwise null.
     */
    public static @Nullable DoublyLocation findDoubly(@NotNull Location location) {
        if (isDoubly(location)) {
            return (DoublyLocation) location;
        }

        if (!BlockUtils.isDoubleChest(location.getBlock())) {
            return null;
        }

        return getAsDoubly(location);
    }


    /**
     * Finds a {@link DoublyLocation doubly location} based on the {@link Block block's location}.
     *
     * @param block the block to base the doubly on.
     *
     * @return the found {@link DoublyLocation}, otherwise null.
     */
    public static @Nullable DoublyLocation findDoubly(@NotNull Block block) {
        return LocationUtils.findDoubly(block.getLocation());
    }


    /**
     * Checks whether the provided {@link Location location} is a {@link DoublyLocation doubly location}.
     *
     * @param location the location to check.
     *
     * @return true if doubly, otherwise false.
     */
    public static boolean isDoubly(@NotNull Location location) {
        return location instanceof DoublyLocation;
    }


    /**
     * Checks whether the provided {@link Block block's location} is a {@link DoublyLocation doubly location}.
     *
     * @param block the block to check.
     *
     * @return true if doubly, otherwise false.
     */
    @SuppressWarnings("unused")
    public static boolean isDoubly(@NotNull Block block) {
        return isDoubly(block.getLocation());
    }


    /**
     * Gets the doubly based on the {@link Location location} and its {@link Block neighboring block}.
     *
     * @param location the location to base the doubly on.
     *
     * @return the found {@link DoublyLocation}, otherwise null.
     */
    @Contract("_ -> new")
    private static @Nullable DoublyLocation getAsDoubly(@NotNull Location location) {
        Location locOne = new Location(
                location.getWorld(),
                location.getX() + 1,
                location.getY(),
                location.getZ()
        );
        Location locTwo = new Location(
                location.getWorld(),
                location.getX() - 1,
                location.getY(),
                location.getZ()
        );
        Location locThree = new Location(
                location.getWorld(),
                location.getX(),
                location.getY(),
                location.getZ() + 1
        );
        Location locFour = new Location(
                location.getWorld(),
                location.getX(),
                location.getY(),
                location.getZ() - 1
        );

        if (AutofarmUtils.isContainer(locOne.getBlock(), true)) {
            return new DoublyLocation(location, locOne);
        }

        if (AutofarmUtils.isContainer(locTwo.getBlock(), true)) {
            return new DoublyLocation(location, locTwo);
        }

        if (AutofarmUtils.isContainer(locThree.getBlock(), true)) {
            return new DoublyLocation(location, locThree);
        }

        if (AutofarmUtils.isContainer(locFour.getBlock(), true)) {
            return new DoublyLocation(location, locFour);
        }
        return null;
    }

}