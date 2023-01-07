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
 * A class that represents the red mushroom crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class RedMushroom extends Mushroom {

    public RedMushroom(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the name of the crop.
     */
    @Override
    public @NotNull String getName() {
        return "redMushroom";
    }


    /**
     * Gets the current age of the {@link Crop crop} provided the {@link Block crop block}.
     *
     * @param block the crop block.
     *
     * @return the current age of the crop.
     */
    @Override
    public int getCurrentAge(@NotNull Block block) {
        mushrooms = new Stack<>();

        Stack<Block> stack = new Stack<>();
        stack.push(block);

        while (stack.size() > 0) {
            Block mushroom = stack.pop();

            if (!isMushroomType(mushroom)) {
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

            stack.push(mushroom.getRelative(1, -1, 0));
            stack.push(mushroom.getRelative(-1, -1, 0));
            stack.push(mushroom.getRelative(0, -1, 1));
            stack.push(mushroom.getRelative(0, -1, -1));

            stack.push(mushroom.getRelative(BlockFace.DOWN));
        }

        return mushrooms.size() + 1;
    }


    /**
     * Gets the {@link Crop crop's} drop.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.RED_MUSHROOM,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 15)
        );
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the clickable type of the crop.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.MUSHROOM_STEM;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the menu type of the crop.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.RED_MUSHROOM;
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link RedMushroom red mushroom}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isRedMushroom(@NotNull Block block) {
        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(0, y, 0);

            if (isRedMushroomType(above)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link RedMushroom red mushroom}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    private boolean isRedMushroomType(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.RED_MUSHROOM_BLOCK);
    }


}