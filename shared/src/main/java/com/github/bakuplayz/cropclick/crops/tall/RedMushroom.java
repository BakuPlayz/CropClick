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

import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropArguments;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractMushroom;
import com.github.bakuplayz.cropclick.crops.algorithms.StackTraversal;
import com.github.bakuplayz.cropclick.crops.algorithms.inputs.RedMushroomInput;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


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

    private final static StackTraversal AGE_ALGORITHM = new StackTraversal();


    public RedMushroom(@NotNull CropArguments arguments) {
        super(arguments);
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

        return AGE_ALGORITHM.getCurrentAge(
                new RedMushroomInput(block, (mushroom) -> !isNotMushroomType(mushroom) || mushrooms.contains(mushroom), mushrooms)
        );
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
        return Blocks.isSameType(block, XMaterial.RED_MUSHROOM_BLOCK);
    }


}