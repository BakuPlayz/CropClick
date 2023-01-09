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

package com.github.bakuplayz.cropclick.crop.crops.wall;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.WallCrop;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the cocoa bean crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class CocoaBean extends WallCrop {

    public CocoaBean(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "cocoaBean";
    }


    /**
     * Gets the harvest age of the {@link Crop crop}.
     *
     * @return the crop's harvest age.
     */
    @Override
    public int getHarvestAge() {
        return 8;
    }


    /**
     * Gets the {@link Crop crop's} drop.
     *
     * @return the crop's drop.
     */
    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.INK_SACK,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 3),
                cropSection.getDropChance(getName(), 80),
                (short) 3
        );
    }


    /**
     * Gets the {@link Crop crop's} clickable type.
     *
     * @return the crop's clickable type.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.COCOA;
    }


    /**
     * Gets the {@link Crop crop's} menu type.
     *
     * @return the crop's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.INK_SACK;
    }


    /**
     * Checks whether the {@link Crop crop} is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false (default: false).
     */
    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
    }

}