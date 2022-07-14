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
 */
public final class AutofarmUtil {

    public static boolean isContainer(@NotNull Block block) {
        if (BlockUtil.isSameType(block, Material.CHEST)) return true;
        return block.getType().name().contains("SHULKER");
    }

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

    public static boolean isDispenser(@NotNull Block block) {
        return BlockUtil.isSameType(block, Material.DISPENSER);
    }

    public static @NotNull Dispenser getDispenser(@NotNull Block block) {
        return (Dispenser) block.getState();
    }

    public static boolean isCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.isCrop(block);
    }

    public static @Nullable Crop getCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.findByBlock(block);
    }

}
