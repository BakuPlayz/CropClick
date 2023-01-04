package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.Mushroom;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;


/**
 * A class that represents the brown mushroom crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @see TallCrop
 * @since 2.0.0
 */
public final class BrownMushroom extends Mushroom {

    public BrownMushroom(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "brownMushroom";
    }


    @Override
    public int getCurrentAge(@NotNull Block clickedBlock) {
        mushrooms = new Stack<>();

        Stack<Block> stack = new Stack<>();
        stack.push(clickedBlock);

        while (stack.size() > 0) {
            Block mushroom = stack.pop();

            if (!isMushroomBlock(mushroom)) {
                continue;
            }

            if (mushrooms.contains(mushroom)) {
                continue;
            }

            mushrooms.add(mushroom);
            stack.push(mushroom.getRelative(BlockFace.UP));
            stack.push(mushroom.getRelative(BlockFace.EAST));
            stack.push(mushroom.getRelative(BlockFace.SOUTH));
            stack.push(mushroom.getRelative(BlockFace.WEST));
            stack.push(mushroom.getRelative(BlockFace.NORTH));
        }

        return mushrooms.size() + 1;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.BROWN_MUSHROOM,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 15)
        );
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.MUSHROOM_STEM;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.BROWN_MUSHROOM;
    }


    /**
     * Returns true if the function ultimately figures out if, the clicked block,
     * is included in a brown mushroom structure.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isBrownMushroom(@NotNull Block block) {
        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(0, y, 0);

            if (isBrownMushroomBlock(above)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Returns true if the block is any type of Brown Mushroom Block.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    private boolean isBrownMushroomBlock(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.BROWN_MUSHROOM_BLOCK);
    }


}