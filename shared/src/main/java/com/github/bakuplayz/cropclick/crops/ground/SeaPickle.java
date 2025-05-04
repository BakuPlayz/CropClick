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

package com.github.bakuplayz.cropclick.crops.ground;

import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropArguments;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.MassHarvestable;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractGroundCrop;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class that represents the sea pickle crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see AbstractCrop
 * @since 2.0.0
 */
public final class SeaPickle extends AbstractGroundCrop implements MassHarvestable {

    public SeaPickle(@NotNull CropArguments arguments) {
        super(arguments);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @Override
    public @NotNull String getName() {
        return "seaPickle";
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @NotNull
    @Override
    public Drop getDrop() {
        return createDrop(1, 100);
    }


    /**
     * Gets the seed of the {@link Crop crop}.
     *
     * @return the crop's seed (default: null).
     */
    @Nullable
    @Override
    public Seed getSeed() {
        return null;
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @NotNull
    @Override
    public XMaterial getClickableType() {
        return XMaterial.SEA_PICKLE;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @NotNull
    @Override
    public XMaterial getMenuType() {
        return XMaterial.SEA_PICKLE;
    }


    /**
     * Gets the harvest age of the {@link Crop crop}.
     *
     * @return the crop's harvest age (default: 2).
     */
    @Override
    public int getHarvestAge() {
        return 2;
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
        return ((org.bukkit.block.data.type.SeaPickle) block.getBlockData()).getPickles();
    }


    /**
     * Replants the {@link Crop crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        if (!shouldReplant()) {
            block.setType(Material.AIR);
            return;
        }

        org.bukkit.block.data.type.SeaPickle seaPickle = (org.bukkit.block.data.type.SeaPickle) block.getBlockData();
        seaPickle.setPickles(1);
        block.setBlockData(seaPickle);
    }

}