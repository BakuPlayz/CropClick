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

package com.github.bakuplayz.cropclick.crop.crops.base;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * An interface representing waterlogged crops.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Waterlogged {

    /**
     * Checks whether the {@link Block provided block} is waterlogged.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    boolean isWaterLogged(@NotNull Block block);

    /**
     * Sets the waterlogged state of the {@link Block provided block} to the provided state.
     *
     * @param block       the block to set the state of.
     * @param waterlogged the state to set.
     */
    void setWaterLogged(@NotNull Block block, boolean waterlogged);

}