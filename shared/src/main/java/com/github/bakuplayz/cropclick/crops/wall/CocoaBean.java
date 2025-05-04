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

package com.github.bakuplayz.cropclick.crops.wall;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropArguments;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractWallCrop;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;

/**
 * A class that represents the cocoa bean crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see AbstractCrop
 * @since 2.0.0
 */
public final class CocoaBean extends AbstractWallCrop {

    public CocoaBean(@NotNull CropArguments arguments) {
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
        return "cocoaBean";
    }


    /**
     * Gets the harvest age of the {@link Crop crop}.
     *
     * @return the crop's harvest age.
     */
    @Override
    public int getHarvestAge() {
        return 2;
    }


    /**
     * Gets the {@link Crop crop's} drop.
     *
     * @return the crop's drop.
     */
    @NotNull
    @Override
    public Drop getDrop() {
        return createDrop(3, 80);
    }


    /**
     * Gets the {@link Crop crop's} clickable type.
     *
     * @return the crop's clickable type.
     */
    @NotNull
    @Override
    public XMaterial getClickableType() {
        return XMaterial.COCOA;
    }


    /**
     * Gets the {@link Crop crop's} menu type.
     *
     * @return the crop's menu type.
     */
    @NotNull
    @Override
    public XMaterial getMenuType() {
        return XMaterial.COCOA_BEANS;
    }


    /**
     * Checks whether the {@link Crop crop} is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false (default: false).
     */
    @Override
    public boolean isLinkable() {
        return cropsConfig.getOrDefault(ConfigurationKey.CROP_LINKABLE, false, getName());
    }

}