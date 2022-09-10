package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see TallCrop
 * @see BaseCrop
 * @see Crop
 * @since 2.0.0
 */
public abstract class Mushroom extends TallCrop {

    protected Stack<Block> mushrooms;


    public Mushroom(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);

        mushrooms = new Stack<>();
    }


    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


    @Override
    public void replant(@NotNull Block clickedBlock) {
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
        return BlockUtils.isAnyType(block, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2);
    }

}