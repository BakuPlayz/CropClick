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

package com.github.bakuplayz.cropclick.crops.tall;

import com.github.bakuplayz.cropclick.common.BlockUtils;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractMushroom;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;


/**
 * A class that represents the red mushroom crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see AbstractCrop
 * @since 2.0.0
 */
public final class RedMushroom extends AbstractMushroom {

    public RedMushroom(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @NotNull
    @Override
    public String getName() {
        return "redMushroom";
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @NotNull
    @Override
    public Drop getDrop() {
        return createDrop(1, 15);
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @NotNull
    @Override
    public XMaterial getClickableType() {
        return XMaterial.MUSHROOM_STEM;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @NotNull
    @Override
    public XMaterial getMenuType() {
        return XMaterial.RED_MUSHROOM;
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
        mushrooms.clear();

        Stack<Block> stack = new Stack<>();
        stack.push(block);

        while (!stack.isEmpty()) {
            Block mushroom = stack.pop();

            if (isNotMushroomType(mushroom)) {
                continue;
            }

            if (mushrooms.contains(mushroom)) {
                continue;
            }

            mushrooms.add(mushroom);
            stack.push(mushroom.getRelative(BlockFace.UP));
            stack.push(mushroom.getRelative(BlockFace.EAST));
            stack.push(mushroom.getRelative(BlockFace.SOUTH));
            stack.push(mushroom.getRelative(BlockFace.WEST));
            stack.push(mushroom.getRelative(BlockFace.NORTH));

            stack.push(mushroom.getRelative(1, -1, 0));
            stack.push(mushroom.getRelative(-1, -1, 0));
            stack.push(mushroom.getRelative(0, -1, 1));
            stack.push(mushroom.getRelative(0, -1, -1));

            stack.push(mushroom.getRelative(BlockFace.DOWN));
        }

        return mushrooms.size() + 1;
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link RedMushroom red mushroom}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isRedMushroom(@NotNull Block block) {
        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(0, y, 0);

            if (isRedMushroomType(above)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link RedMushroom red mushroom}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    private boolean isRedMushroomType(@NotNull Block block) {
        return BlockUtils.isSameType(block, XMaterial.RED_MUSHROOM_BLOCK);
    }


}