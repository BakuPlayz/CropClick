package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;


/**
 * A class that represents the base of a mushroom crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public abstract class Mushroom extends TallCrop {

    /**
     * A variable containing all the mushroom blocks as a stack instead of a list, due to their in-game structure.
     */
    protected Stack<Block> mushrooms;


    public Mushroom(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);

        mushrooms = new Stack<>();
    }


    /**
     * Checks whether at least one mushroom should drop.
     *
     * @return true if it should, otherwise false (default: false).
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


    /**
     * Replants the mushroom.
     *
     * @param block the mushroom crop block.
     */
    @Override
    public void replant(@NotNull Block block) {
        mushrooms.forEach(b -> b.setType(Material.AIR));

        mushrooms = new Stack<>();
    }


    /**
     * Returns true if the block is any type of Mushroom Block or Mushroom Stem.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean isMushroomBlock(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.MUSHROOM_STEM, Material.BROWN_MUSHROOM_BLOCK, Material.RED_MUSHROOM_BLOCK);
    }

}