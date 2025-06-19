/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.crops;

import com.github.bakuplayz.cropclick.autofarm.Container;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public interface MassHarvestable extends Crop {

    /**
     * Harvests all the {@link Crop crops}.
     *
     * @param container the container to add the drops to.
     * @param block     the crop block that was harvested.
     *
     * @return true if it harvested all, otherwise false.
     */
    default boolean harvestAll(@NotNull Container container, @NotNull Block block) {
        boolean wasHarvested = true;

        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            if (!wasHarvested) {
                return false;
            }

            wasHarvested = harvest(container);
        }

        return wasHarvested;
    }

}
