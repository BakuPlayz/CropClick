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

package com.github.bakuplayz.cropclick.crop.seeds.base;

import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * An interface acting as a base for a seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Seed {

    /**
     * Gets the name of the implementing seed.
     *
     * @return the name of the seed.
     */
    @NotNull
    String getName();


    /**
     * Gets the {@link Drop drop} of the implementing seed.
     *
     * @return the drop of the seed.
     */
    Drop getDrop();


    /**
     * Checks whether the implementing seed has a drop.
     *
     * @return true if it has, otherwise false.
     */
    boolean hasDrop();


    /**
     * Harvests the implementing seed.
     *
     * @param inventory the inventory to add the drops to.
     *
     * @return true if the seed was harvested, otherwise false.
     */
    boolean harvest(@NotNull Inventory inventory);


    /**
     * Gets the {@link Material menu type} of the implementing seed.
     *
     * @return the seed's menu type.
     */
    @NotNull Material getMenuType();


    /**
     * Checks whether the implementing seed is enabled.
     *
     * @return true if it is enabled, otherwise false.
     */
    boolean isEnabled();

}