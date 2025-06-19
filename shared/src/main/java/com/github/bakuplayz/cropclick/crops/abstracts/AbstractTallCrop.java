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

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.Container;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropArguments;
import com.github.bakuplayz.cropclick.crops.MassHarvestable;
import com.github.bakuplayz.cropclick.crops.algorithms.AgeBottomTopTraversal;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;


/**
 * A class that represents the base of a tall crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class AbstractTallCrop extends AbstractCrop implements MassHarvestable {

    private final static AgeBottomTopTraversal AGE_ALGORITHM = new AgeBottomTopTraversal();


    public AbstractTallCrop(@NotNull CropArguments arguments) {
        super(arguments);
    }


    /**
     * Gets the harvest age of the {@link AbstractTallCrop extending tall crop}.
     *
     * @return the crop's harvest age (default: 2).
     */
    @Override
    public int getHarvestAge() {
        return 2;
    }


    /**
     * Gets the {@link Seed seed} of the {@link AbstractTallCrop extending tall crop}.
     *
     * @return the seed, otherwise null (default: null).
     */
    @Nullable
    @Override
    public Seed getSeed() {
        return null;
    }


    /**
     * Gets the current age of the {@link AbstractTallCrop extending tall crop}.
     *
     * @param block the crop block.
     *
     * @return the crop's current age.
     */
    @Override
    public int getCurrentAge(@NotNull Block block) {
        return AGE_ALGORITHM.getCurrentAge(block);
    }


    /**
     * Checks whether the {@link AbstractTallCrop extending tall crop} has a {@link Seed seed}.
     *
     * @return true if it has, otherwise false (default: false).
     */
    @Override
    public boolean hasSeed() {
        return false;
    }


    /**
     * Checks whether the {@link AbstractTallCrop extending tall crop} is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false (default: false).
     */
    @Override
    public boolean isLinkable() {
        return cropsConfig.getBoolean(ConfigurationKey.CROP_LINKABLE, getName());
    }


    /**
     * Replants the {@link AbstractTallCrop extending tall crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        int height = getCurrentAge(block);
        for (int y = height; y > 0; --y) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    block.getY() + y,
                    block.getZ()
            );
            currentBlock.setType(Material.AIR);
        }

        if (!shouldReplant()) {
            block.setType(Material.AIR);
        }
    }


    /**
     * Harvests all the {@link AbstractTallCrop extending tall crops}.
     *
     * @param container the container to add the drops to.
     * @param block     the crop block that was harvested.
     *
     * @return true if it harvested all, otherwise false.
     */
    public boolean harvestAll(@NotNull Container container, @NotNull Block block) {
        boolean wasHarvested = true;

        int height = getCurrentAge(block);
        int actualHeight = getActualHeight(height);
        for (int i = actualHeight; i > 0; --i) {
            if (!wasHarvested) {
                return false;
            }

            wasHarvested = harvest(container);
        }

        return wasHarvested;
    }


    /**
     * Gets the actual height/age of the {@link AbstractTallCrop extending tall crop}.
     *
     * @param age the age of the crop.
     *
     * @return the actual height/age of the crop.
     */
    private int getActualHeight(int age) {
        return shouldReplant() ? age - 1 : age;
    }

}