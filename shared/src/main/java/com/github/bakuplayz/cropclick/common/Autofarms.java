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

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.Container;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;


/**
 * A utility class for {@link Autofarm autofarms}, its {@link AutofarmManager manager} and its events.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class Autofarms {

    public static boolean areComponents(@NotNull CropManager cropManager, @NotNull Block... blocks) {
        return Arrays.stream(blocks).allMatch(b -> isComponent(cropManager, b));
    }


    /**
     * Checks whether the {@link Block provided block} is a component (container, crop or dispenser).
     *
     * @param cropManager the crop manager.
     * @param block       the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isComponent(@NotNull CropManager cropManager, @NotNull Block block) {
        if (Autofarms.isDispenser(block)) return true;
        if (Autofarms.isContainer(block)) return true;
        return Autofarms.isCrop(cropManager, block);
    }


    /**
     * Checks whether the {@link Block provided block} is a container.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isContainer(@NotNull Block block) {
        return Blocks.isAnyType(block, XMaterial.CHEST, XMaterial.SHULKER_BOX, XMaterial.BARREL);
    }


    /**
     * Finds a {@link Container container} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the container, otherwise null.
     */
    @Nullable
    public static Container findContainer(@NotNull Block block) {
        return Container.fromBlock(block);
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Dispenser dispenser}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isDispenser(@NotNull Block block) {
        return Blocks.isSameType(block, XMaterial.DISPENSER);
    }


    /**
     * Finds a {@link Dispenser dispenser} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the dispenser, otherwise null.
     */

    @NotNull
    public static Dispenser findDispenser(@NotNull Block block) {
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
        return manager.getFinder().isCrop(block);
    }


    /**
     * Finds a {@link Crop crop} based on the {@link Block provided block}.
     *
     * @param manager the crop manager.
     * @param block   the block to base the findings on.
     *
     * @return the crop, otherwise null.
     */
    @Nullable
    public static Crop findCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.getFinder().findByBlock(block);
    }


}