package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @see Crop
 * @since 2.0.0
 */
public final class BrownMushroom extends TallCrop {

    public BrownMushroom(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "brownMushroom";
    }


    //TODO: Check for actual mushrooms, and not a predefined amount.
    @Override
    public int getCurrentAge(@NotNull Block block) {
        return isBrownMushroom(block) ? 45 : 0;
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
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


    //TODO: Check for actual mushrooms, and not a predefined amount.
    @Override
    public void replant(@NotNull Block block) {
        Block start = block;

        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(BlockFace.UP, y);

            if (isMushroomBlock(above)) {
                start = above;
                break;
            }

            above.setType(Material.AIR);
        }

        for (int x = -4; x < 4; ++x) {
            for (int z = -4; z < 4; ++z) {

                Block mushroom = start.getWorld().getBlockAt(
                        start.getX() + x,
                        start.getY(),
                        start.getZ() + z
                );

                if (isMushroomBlock(mushroom)) {
                    //start =
                }

            }
        }

        block.setType(Material.AIR);
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
     * Returns true if the block is any type of Brown Mushroom.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isBrownMushroom(@NotNull Block block) {
        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(BlockFace.UP, y);

            if (isMushroomBlock(above)) {
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
    private boolean isMushroomBlock(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.BROWN_MUSHROOM_BLOCK);
    }

}