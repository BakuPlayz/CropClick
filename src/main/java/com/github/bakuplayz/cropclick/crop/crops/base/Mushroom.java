package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
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


    public Mushroom(@NotNull CropsConfig config) {
        super(config);

        mushrooms = new Stack<>();
    }


    /**
     * Checks whether the {@link Mushroom extending mushroom} should drop at least one {@link Drop drop}.
     *
     * @return true if it should, otherwise false (default: false).
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


    /**
     * Replants the {@link Mushroom extending mushroom}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        mushrooms.forEach(b -> b.setType(Material.AIR));

        mushrooms = new Stack<>();
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Mushroom mushroom} block.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean isMushroomType(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2);
    }

}