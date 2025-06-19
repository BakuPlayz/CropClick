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

package com.github.bakuplayz.cropclick.crops.roof;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropArguments;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractRoofCrop;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;


/**
 * A class that represents the glow berries crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see AbstractCrop
 * @since 2.0.0
 */
public final class GlowBerries extends AbstractRoofCrop {

    private final List<Integer> berryYPositions;


    public GlowBerries(@NotNull CropArguments arguments) {
        super(arguments);

        this.berryYPositions = new ArrayList<>();
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @NotNull
    @Override
    public String getName() {
        return "glowBerries";
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @NotNull
    @Override
    public Drop getDrop() {
        return createDrop(1, 1.0d);
    }


    @Override
    public boolean isAlreadyClickable() {
        return true;
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @NotNull
    @Override
    public XMaterial getClickableType() {
        return XMaterial.CAVE_VINES_PLANT;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @NotNull
    @Override
    public XMaterial getMenuType() {
        return XMaterial.GLOW_BERRIES;
    }


    /**
     * Gets the harvest age of the {@link Crop crop}.
     *
     * @return the crop's harvest age (default: 1).
     */
    @Override
    public int getHarvestAge() {
        return 1;
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
        int minHeight = block.getLocation().getBlockY();
        int maxHeight = block.getWorld().getMaxHeight();

        berryYPositions.clear();

        int height = 0;
        for (int y = minHeight; y <= maxHeight; ++y) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    y,
                    block.getZ()
            );

            if (Blocks.isAir(currentBlock)) {
                break;
            }

            if (!isGlowBerriesType(currentBlock)) {
                break;
            }

            CaveVinesPlant vines = (CaveVinesPlant) currentBlock.getBlockData();
            if (vines.isBerries()) {
                berryYPositions.add(currentBlock.getY());
                ++height;
            }
        }

        return height;
    }


    /**
     * Checks whether the {@link Crop crop} is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isLinkable() {
        return cropsConfig.getBooleanOrDefault(ConfigurationKey.CROP_LINKABLE, false, getName());
    }


    /**
     * Replants the {@link Crop crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        for (int y : berryYPositions) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    y,
                    block.getZ()
            );

            CaveVinesPlant vines = (CaveVinesPlant) currentBlock.getBlockData();
            if (vines.isBerries()) {
                vines.setBerries(false);
                currentBlock.setBlockData(vines);
            }
        }
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link GlowBerries glow berries}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isGlowBerriesType(@NotNull Block block) {
        return Blocks.isAnyType(block, XMaterial.CAVE_VINES, XMaterial.CAVE_VINES_PLANT);
    }

}