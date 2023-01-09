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

package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Waterlogged;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the dripleaf crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Dripleaf extends TallCrop implements Waterlogged {

    public Dripleaf(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @Override
    public @NotNull String getName() {
        return "dripleaf";
    }


    /**
     * Gets the current age of the {@link Crop crop} provided the {@link Block crop block}.
     *
     * @param block the crop block.
     *
     * @return the crop's current age.
     */
    @Override
    public int getCurrentAge(@NotNull Block block) {
        int minHeight = block.getLocation().getBlockY();
        int maxHeight = block.getWorld().getMaxHeight();

        int height = 0;
        for (int y = minHeight; y <= maxHeight; ++y) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    y,
                    block.getZ()
            );

            if (BlockUtils.isAir(currentBlock)) {
                break;
            }

            if (isDripleafType(currentBlock)) {
                ++height;
            }
        }

        return height;
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.BIG_DRIPLEAF,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
    }


    /**
     * Replants the {@link Crop crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        boolean isWaterLogged = false;

        int height = getCurrentAge(block);
        for (int y = height; y > 0; --y) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    block.getY() + y,
                    block.getZ()
            );

            if (!isWaterLogged) {
                isWaterLogged = isWaterLogged(currentBlock);
            }

            currentBlock.setType(
                    isWaterLogged ? Material.WATER : Material.AIR
            );
        }

        if (!shouldReplant()) {
            block.setType(
                    isWaterLogged ? Material.WATER : Material.AIR
            );
            return;
        }

        block.setType(Material.BIG_DRIPLEAF);
        setWaterLogged(block, isWaterLogged);
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.BIG_DRIPLEAF_STEM;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.BIG_DRIPLEAF;
    }


    /**
     * Checks whether the {@link Block provided block} is {@link Waterlogged waterlogged}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isWaterLogged(@NotNull Block block) {
        if (!(block.getBlockData() instanceof org.bukkit.block.data.type.Dripleaf)) {
            return false;
        }
        return ((org.bukkit.block.data.type.Dripleaf) block.getBlockData()).isWaterlogged();
    }


    /**
     * Sets the {@link Waterlogged waterlogged state} of the {@link Block provided block} to the provided state.
     *
     * @param block       the block to set the state of.
     * @param waterlogged the state to set.
     */
    @Override
    public void setWaterLogged(@NotNull Block block, boolean waterlogged) {
        org.bukkit.block.data.Waterlogged dripleaf = (org.bukkit.block.data.Waterlogged) block.getBlockData();
        dripleaf.setWaterlogged(waterlogged);
        block.setBlockData(dripleaf);
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link Dripleaf drip leaf}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isDripleafType(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.BIG_DRIPLEAF, Material.BIG_DRIPLEAF_STEM);
    }

}