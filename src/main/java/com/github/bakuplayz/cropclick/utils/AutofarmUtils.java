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

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.autofarm.container.ContainerType;
import com.github.bakuplayz.cropclick.autofarm.metadata.AutofarmMetadata;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * A utility class for {@link Autofarm autofarms}, its {@link AutofarmManager manager} and its events.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmUtils {

    /**
     * The {@link MetadataValue meta key} for the cached {@link Autofarm#getFarmerID() farmer id}.
     */
    public static final String FARMER_ID_KEY = "farmerID";


    /**
     * Checks whether the {@link Player provided player} is the owner of the {@link Autofarm provided autofarm}.
     *
     * @param player   the player to check.
     * @param autofarm the farm to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isOwner(@NotNull Player player, @NotNull Autofarm autofarm) {
        return autofarm.getOwnerID().equals(player.getUniqueId());
    }


    /**
     * Checks whether the {@link Block provided block} is a container.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isContainer(@NotNull Block block) {
        if (BlockUtils.isSameType(block, Material.CHEST)) {
            return true;
        }
        return block.getType().name().contains("SHULKER");
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Container container}.
     *
     * @param block           the block to check.
     * @param onlyDoubleChest match for only double chests.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isContainer(@NotNull Block block, boolean onlyDoubleChest) {
        if (onlyDoubleChest) {
            return BlockUtils.isDoubleChest(block);
        }
        return AutofarmUtils.isContainer(block);
    }


    /**
     * Finds a {@link Container container} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the container, otherwise null.
     */
    public static @Nullable Container findContainer(@NotNull Block block) {
        BlockState blockState = block.getState();

        if (BlockUtils.isDoubleChest(block)) {
            return new Container(
                    ((Chest) blockState).getInventory(),
                    ContainerType.DOUBLE_CHEST
            );
        }

        if (blockState instanceof Chest) {
            return new Container(
                    ((Chest) blockState).getInventory(),
                    ContainerType.CHEST
            );
        }

        if (blockState instanceof ShulkerBox) {
            return new Container(
                    ((ShulkerBox) blockState).getInventory(),
                    ContainerType.SHULKER
            );
        }

        return null;
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Dispenser dispenser}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isDispenser(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.DISPENSER);
    }


    /**
     * Finds a {@link Dispenser dispenser} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the dispenser, otherwise null.
     */
    public static @NotNull Dispenser findDispenser(@NotNull Block block) {
        return (Dispenser) block.getState();
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Crop crop}.
     *
     * @param manager the crop manager.
     * @param block   the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.isCrop(block);
    }


    /**
     * Finds a {@link Crop crop} based on the {@link Block provided block}.
     *
     * @param manager the crop manager.
     * @param block   the block to base the findings on.
     *
     * @return the crop, otherwise null.
     */
    public static @Nullable Crop findCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.findByBlock(block);
    }


    /**
     * Adds the cached {@link Autofarm#getFarmerID() autofarmer ID} to all the {@link Autofarm autofarm's} components.
     *
     * @param plugin   the CropClick instance.
     * @param autofarm the farm to add the ID to.
     */
    public static void addCachedID(@NotNull CropClick plugin, @NotNull Autofarm autofarm) {
        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        AutofarmMetadata farmerMeta = new AutofarmMetadata(plugin, autofarm::getFarmerID);

        DoublyLocation doublyLocation = LocationUtils.findDoubly(container);
        if (doublyLocation != null) {
            Block singly = doublyLocation.getSingly().getBlock();
            Block doubly = doublyLocation.getDoubly().getBlock();

            singly.setMetadata(AutofarmUtils.FARMER_ID_KEY, farmerMeta);
            doubly.setMetadata(AutofarmUtils.FARMER_ID_KEY, farmerMeta);

            autofarm.setContainerLocation(doublyLocation);
        } else {
            container.setMetadata(AutofarmUtils.FARMER_ID_KEY, farmerMeta);
        }

        dispenser.setMetadata(AutofarmUtils.FARMER_ID_KEY, farmerMeta);
        crop.setMetadata(AutofarmUtils.FARMER_ID_KEY, farmerMeta);
    }


    /**
     * Removes the cached {@link Autofarm#getFarmerID() autofarmer ID} from all the {@link Autofarm autofarm's} components.
     *
     * @param plugin   the CropClick instance.
     * @param autofarm the farm to remove the ID from.
     */
    public static void removeCachedID(@NotNull CropClick plugin, @NotNull Autofarm autofarm) {
        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        DoublyLocation doublyContainer = LocationUtils.findDoubly(container);
        if (doublyContainer != null) {
            Block singly = doublyContainer.getSingly().getBlock();
            Block doubly = doublyContainer.getDoubly().getBlock();

            singly.removeMetadata(AutofarmUtils.FARMER_ID_KEY, plugin);
            doubly.removeMetadata(AutofarmUtils.FARMER_ID_KEY, plugin);
        } else {
            container.removeMetadata(AutofarmUtils.FARMER_ID_KEY, plugin);
        }

        dispenser.removeMetadata(AutofarmUtils.FARMER_ID_KEY, plugin);
        crop.removeMetadata(AutofarmUtils.FARMER_ID_KEY, plugin);
    }


    /**
     * Checks whether the {@link Autofarm autofarm's} components has a cached {@link Autofarm#getFarmerID() autofarmer ID}.
     *
     * @param autofarm the farm to check.
     *
     * @return true if they have it, otherwise false.
     */
    public static boolean hasCachedID(@NotNull Autofarm autofarm) {
        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        if (!AutofarmUtils.hasCachedID(dispenser)) {
            return false;
        }

        if (!AutofarmUtils.hasCachedID(crop)) {
            return false;
        }

        DoublyLocation doublyContainer = LocationUtils.findDoubly(container);
        if (doublyContainer != null) {
            Block singly = doublyContainer.getSingly().getBlock();
            Block doubly = doublyContainer.getSingly().getBlock();

            if (!AutofarmUtils.hasCachedID(singly)) {
                return false;
            }

            if (!AutofarmUtils.hasCachedID(doubly)) {
                return false;
            }
        }

        return AutofarmUtils.hasCachedID(container);
    }


    /**
     * Checks whether the {@link Block provided block} has the cached {@link Autofarm#getFarmerID() autofarmer ID}.
     *
     * @param block the block to check.
     *
     * @return true if it has, otherwise false.
     */
    public static boolean hasCachedID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(AutofarmUtils.FARMER_ID_KEY);
        return block.hasMetadata(AutofarmUtils.FARMER_ID_KEY) && !metas.isEmpty();
    }


    /**
     * Gets the cached {@link Autofarm#getFarmerID() autofarmer ID} based on the {@link Block provided block}.
     *
     * @param block the block to get the cached ID from.
     *
     * @return the found ID, otherwise null.
     */
    public static @Nullable String getCachedID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(AutofarmUtils.FARMER_ID_KEY);
        return metas.isEmpty() ? null : metas.get(0).asString();
    }

}