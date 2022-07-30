package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.autofarm.container.ContainerType;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AutofarmUtil {

    /**
     * If the block is a chest or shulker box, return true.
     *
     * @param block The block to check.
     *
     * @return A boolean value.
     */
    public static boolean isContainer(@NotNull Block block) {
        if (BlockUtil.isSameType(block, Material.CHEST)) {
            return true;
        }
        return block.getType().name().contains("SHULKER");
    }


    /**
     * Returns true if the block is a container, false otherwise.
     *
     * @param block           The block to check.
     * @param onlyDoubleChest If true, only double chests will be considered containers.
     *
     * @return A boolean value.
     */
    public static boolean isContainer(@NotNull Block block, boolean onlyDoubleChest) {
        if (onlyDoubleChest) {
            return block.getState() instanceof DoubleChest;
        }
        return isContainer(block);
    }


    /**
     * If the block is a chest, double chest, or shulker box, return a new Container object with the block's inventory and
     * the appropriate container type.
     *
     * @param block The block to get the container from.
     *
     * @return A Container object
     */
    public static @Nullable Container getContainer(@NotNull Block block) {
        if (block instanceof DoubleChest) {
            return new Container(((DoubleChest) block).getInventory(), ContainerType.DOUBLE_CHEST);
        }

        if (block instanceof Chest) {
            return new Container(((Chest) block).getInventory(), ContainerType.CHEST);
        }

        if (VersionUtil.supportsShulkers()) {
            return new Container(((ShulkerBox) block).getInventory(), ContainerType.SHULKER);
        }

        return null;
    }


    /**
     * Returns true if the given block is a dispenser.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public static boolean isDispenser(@NotNull Block block) {
        return BlockUtil.isSameType(block, Material.DISPENSER);
    }


    /**
     * Returns the Dispenser object associated with the given Block.
     *
     * @param block The block to get the dispenser from.
     *
     * @return A Dispenser object.
     */
    public static @NotNull Dispenser getDispenser(@NotNull Block block) {
        return (Dispenser) block.getState();
    }


    /**
     * Returns true if the block is a crop.
     *
     * @param manager The CropManager instance.
     * @param block   The block to check
     *
     * @return A boolean value.
     */
    public static boolean isCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.isCrop(block);
    }


    /**
     * Returns the crop that the given block is a part of, or null if the block is not a part of a crop.
     *
     * @param manager The CropManager instance.
     * @param block   The block that you want to get the crop from.
     *
     * @return A Crop object
     */
    public static @Nullable Crop getCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.findByBlock(block);
    }

}