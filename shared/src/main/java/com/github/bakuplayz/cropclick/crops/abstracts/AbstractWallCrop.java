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

import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropArguments;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class that represents the base of a wall crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class AbstractWallCrop extends AbstractCrop {

    public AbstractWallCrop(@NotNull CropArguments arguments) {
        super(arguments);
    }


    /**
     * Gets the {@link Seed seed} of the {@link AbstractWallCrop extending wall crop}.
     *
     * @return the seed, otherwise null (default: null).
     */
    @Override
    @Nullable
    public Seed getSeed() {
        return null;
    }


    /**
     * Checks whether the {@link AbstractWallCrop extending wall crop} has a {@link Seed seed}.
     *
     * @return true if it has, otherwise false (default: false).
     */
    @Override
    public boolean hasSeed() {
        return false;
    }

}