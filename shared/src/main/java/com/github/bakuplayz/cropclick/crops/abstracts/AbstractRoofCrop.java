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

import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.algorithms.BottomToTopTraversal;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class that represents the base of a roof crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class AbstractRoofCrop extends AbstractCrop {

    private final static BottomToTopTraversal ALGORITHM = new BottomToTopTraversal();


    public AbstractRoofCrop(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the harvest age of the {@link AbstractRoofCrop extending roof crop}.
     *
     * @return the crop's harvest age (default: 2).
     */
    @Override
    public int getHarvestAge() {
        return 2;
    }


    /**
     * Gets the {@link Seed seed} of the {@link AbstractTallCrop extending roof crop}.
     *
     * @return the crop's seed, otherwise null (default: null).
     */
    @Nullable
    @Override
    public Seed getSeed() {
        return null;
    }


    /**
     * Gets the current age of the {@link AbstractRoofCrop extending roof crop}.
     *
     * @param block the crop block.
     *
     * @return the crop's current age.
     */
    @Override
    public int getCurrentAge(@NotNull Block block) {
        return ALGORITHM.traverse(block);
    }


    /**
     * Checks whether the {@link AbstractRoofCrop extending roof crop} has a {@link Seed seed}.
     *
     * @return true if it has, otherwise false (default: false).
     */
    @Override
    public boolean hasSeed() {
        return false;
    }


    /**
     * Harvests all the {@link AbstractRoofCrop extending roof crops}.
     *
     * @param container the container to add the drops to.
     * @param block     the crop block that was harvested.
     * @param crop      the crop that was harvested.
     *
     * @return true if it harvested all, otherwise false.
     */
    public boolean harvestAll(@NotNull ContainerComponent container, @NotNull Block block, @NotNull Crop crop) {
        boolean wasHarvested = true;

        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            if (!wasHarvested) {
                return false;
            }

            wasHarvested = crop.harvest(container);
        }

        return wasHarvested;
    }

}