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

package com.github.bakuplayz.cropclick.crops.abstracts;

import com.github.bakuplayz.cropclick.common.BlockUtils;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

import static com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;


/**
 * A class that represents the base of a mushroom crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see AbstractCrop
 * @since 2.0.0
 */
public abstract class AbstractMushroom extends AbstractTallCrop {

    protected final Stack<Block> mushrooms;


    public AbstractMushroom(@NotNull CropsConfig config) {
        super(config);

        this.mushrooms = new Stack<>();
    }


    /**
     * Checks whether the {@link AbstractMushroom extending mushroom} should drop at least one {@link Drop drop}.
     *
     * @return true if it should, otherwise false (default: false).
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropsConfig.get(ConfigurationKey.CROP_DROP_AT_LEAST_ONE, getName());
    }


    /**
     * Replants the {@link AbstractMushroom extending mushroom}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        mushrooms.forEach(b -> b.setType(Material.AIR));
        mushrooms.clear();
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link AbstractMushroom mushroom} block.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    protected boolean isNotMushroomType(@NotNull Block block) {
        return !BlockUtils.isAnyType(block, XMaterial.MUSHROOM_STEM, XMaterial.BROWN_MUSHROOM_BLOCK, XMaterial.RED_MUSHROOM_BLOCK);
    }

}