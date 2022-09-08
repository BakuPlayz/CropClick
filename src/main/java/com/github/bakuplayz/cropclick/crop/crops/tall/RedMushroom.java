package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.Mushroom;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @see Crop
 * @since 2.0.0
 */
public final class RedMushroom extends Mushroom {


    public RedMushroom(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "redMushroom";
    }


    @Override
    public int getCurrentAge(@NotNull Block clickedBlock) {
        mushrooms = new Stack<>();

        Stack<Block> stack = new Stack<>();
        stack.push(clickedBlock);

        while (stack.size() > 0) {
            Block mushroom = stack.pop();

            if (!isMushroomBlock(mushroom) || mushrooms.contains(mushroom)) {
                continue;
            }

            mushrooms.add(mushroom);
            stack.push(mushroom.getRelative(BlockFace.UP));
            stack.push(mushroom.getRelative(BlockFace.EAST));
            stack.push(mushroom.getRelative(BlockFace.SOUTH));
            stack.push(mushroom.getRelative(BlockFace.WEST));
            stack.push(mushroom.getRelative(BlockFace.NORTH));

            stack.push(mushroom.getRelative(1, -1, 0));
            stack.push(mushroom.getRelative(-1, -1, 0));
            stack.push(mushroom.getRelative(0, -1, 1));
            stack.push(mushroom.getRelative(0, -1, -1));

            stack.push(mushroom.getRelative(BlockFace.DOWN));
        }

        return mushrooms.size() + 1;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.RED_MUSHROOM,
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
        return Material.RED_MUSHROOM;
    }


    /**
     * Returns true if the function ultimately figures out if, the clicked block,
     * is included in a red mushroom structure.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isRedMushroom(@NotNull Block block) {
        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(0, y, 0);

            if (isRedMushroomBlock(above)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Returns true if the block is any type of Red Mushroom Block.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isRedMushroomBlock(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.RED_MUSHROOM_BLOCK);
    }


}