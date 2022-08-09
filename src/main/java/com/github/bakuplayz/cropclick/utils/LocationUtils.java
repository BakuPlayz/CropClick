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
 * @version 2.0.0
 * @since 2.0.0
 */
public final class LocationUtils {

    /**
     * If the given location is a container, return the location of the container.
     *
     * @param location The location of the block you want to check.
     *
     * @return A DoublyLocation object.
     */
    @Contract(pure = true)
    public static @Nullable DoublyLocation getAsDoubly(@NotNull Location location) {
        if (isDoubly(location)) {
            return (DoublyLocation) location;
        }

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


    /**
     * Finds the DoublyLocation that is at the given Block's location.
     *
     * @param block The block to find the location of.
     *
     * @return A DoublyLocation object.
     */
    public static DoublyLocation getAsDoubly(@NotNull Block block) {
        return LocationUtils.getAsDoubly(block.getLocation());
    }


    /**
     * Returns true if the given location is a doubly location.
     *
     * @param location The location to check.
     *
     * @return A boolean value.
     */
    public static boolean isDoubly(@NotNull Location location) {
        return location instanceof DoublyLocation;
    }


    /**
     * Returns true if the block's location is a doubly location.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    @SuppressWarnings("unused")
    public static boolean isDoubly(@NotNull Block block) {
        return block.getLocation() instanceof DoublyLocation;
    }

}