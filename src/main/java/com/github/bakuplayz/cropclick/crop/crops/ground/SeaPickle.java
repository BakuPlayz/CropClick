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

package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class that represents the sea pickle crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class SeaPickle extends GroundCrop {

    public SeaPickle(@NotNull CropsConfig config) {
        super(config);
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
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.SEA_PICKLE,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
    }


    /**
     * Gets the seed of the {@link Crop crop}.
     *
     * @return the crop's seed (default: null).
     */
    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.SEA_PICKLE;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.SEA_PICKLE;
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
        }

        org.bukkit.block.data.type.SeaPickle seaPickle = (org.bukkit.block.data.type.SeaPickle) block.getBlockData();
        seaPickle.setPickles(1);
        block.setBlockData(seaPickle);
    }


    /**
     * Harvests all the {@link SeaPickle sea pickles}.
     *
     * @param player the player to add the drops to.
     * @param block  the crop block that was harvested.
     * @param crop   the crop that was harvested.
     *
     * @return true if it harvested all, otherwise false.
     */
    public boolean harvestAll(@NotNull Player player, @NotNull Block block, @NotNull Crop crop) {
        boolean wasHarvested = true;

        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            if (!wasHarvested) {
                return false;
            }

            wasHarvested = crop.harvest(player);
        }

        return wasHarvested;
    }


    /**
     * Harvests all the {@link SeaPickle sea pickles}.
     *
     * @param container the container to add the drops to.
     * @param block     the crop block that was harvested.
     * @param crop      the crop that was harvested.
     *
     * @return true if it harvested all, otherwise false.
     */
    public boolean harvestAll(@NotNull Container container, @NotNull Block block, @NotNull Crop crop) {
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