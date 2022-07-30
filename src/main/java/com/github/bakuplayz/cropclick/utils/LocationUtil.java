package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.location.DoublyLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class LocationUtil {

    /**
     * If the given location is a container, return the location of the container.
     *
     * @param location The location of the block you want to check.
     *
     * @return A DoublyLocation object.
     */
    @Contract(pure = true)
    public static @Nullable DoublyLocation findByLocation(@NotNull Location location) {
        if (!isDoublyLocation(location)) return null;

        Location locOne = location.add(1, 0, 0);
        Location locTwo = location.add(-1, 0, 0);
        Location locThree = location.add(0, 0, 1);
        Location locFour = location.add(0, 0, -1);

        if (AutofarmUtil.isContainer(locOne.getBlock(), true)) {
            return new DoublyLocation(location, locOne);
        }

        if (AutofarmUtil.isContainer(locTwo.getBlock(), true)) {
            return new DoublyLocation(location, locTwo);
        }

        if (AutofarmUtil.isContainer(locThree.getBlock(), true)) {
            return new DoublyLocation(location, locThree);
        }

        if (AutofarmUtil.isContainer(locFour.getBlock(), true)) {
            return new DoublyLocation(location, locFour);
        }

        return null;
    }


    /**
     * Finds the DoublyLocation that is at the given Block's location.
     *
     * @param block The block to find the location of.
     *
     * @return A DoublyLocation object.
     */
    public static DoublyLocation findByBlock(@NotNull Block block) {
        return LocationUtil.findByLocation(block.getLocation());
    }


    /**
     * Returns true if the given location is a doubly location.
     *
     * @param location The location to check.
     *
     * @return A boolean value.
     */
    public static boolean isDoublyLocation(@NotNull Location location) {
        return location instanceof DoublyLocation;
    }

}