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

package com.github.bakuplayz.cropclick.crops.tall;

import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropArguments;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractTallCrop;
import com.github.bakuplayz.cropclick.crops.algorithms.StackTraversal;
import com.github.bakuplayz.cropclick.crops.algorithms.inputs.TraversalInput;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;


/**
 * A class that represents the chorus crop.
 *
 * @author BakuPlayz, <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @see Crop
 * @see AbstractCrop
 * @since 2.0.0
 */
public final class Chorus extends AbstractTallCrop {

    private final static StackTraversal ALGORITHM = new StackTraversal();

    private final List<Block> choruses;


    public Chorus(@NotNull CropArguments arguments) {
        super(arguments);

        this.choruses = new ArrayList<>();
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @NotNull
    @Override
    public String getName() {
        return "chorus";
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @NotNull
    @Override
    public Drop getDrop() {
        return createDrop(1, 80);
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @NotNull
    @Override
    public XMaterial getClickableType() {
        return XMaterial.CHORUS_PLANT;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @NotNull
    @Override
    public XMaterial getMenuType() {
        return XMaterial.CHORUS_FRUIT;
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
        choruses.clear();

        return ALGORITHM.getCurrentAge(
                new TraversalInput(block, (chorus) -> !isChorusType(chorus) || choruses.contains(chorus), choruses)
        );
    }


    /**
     * Replants the {@link Crop crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        for (int i = choruses.size() - 1; i >= 0; --i) {
            choruses.get(i).setType(Material.AIR);
        }
    }


    /**
     * Checks whether the {@link Crop crop} should drop at least one drop.
     *
     * @return true if it should, otherwise false (default: false).
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropsConfig.getOrDefault(ConfigurationKey.CROP_DROP_AT_LEAST_ONE, false, getName());
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link Chorus chorus}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isChorusType(@NotNull Block block) {
        return Blocks.isAnyType(block, XMaterial.CHORUS_PLANT, XMaterial.CHORUS_FLOWER);
    }

}