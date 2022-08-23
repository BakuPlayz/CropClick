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
public final class RedMushroom extends TallCrop {

    public RedMushroom(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "redMushroom";
    }


    @Override
    public int getCurrentAge(@NotNull Block block) {
        return isRedMushroom(block) ? 45 : 0;
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
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


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

        for (int x = -3; x < 3; ++x) {
            for (int z = -3; z < 3; ++z) {
                start.getWorld().getBlockAt(
                        start.getX() + x,
                        start.getY(),
                        start.getZ() + z
                ).setType(Material.AIR);
            }
        }

        for (int i = 0; i < 4; ++i) {
            for (int y = -3; y < 3; ++y) {
                for (int z = -3; z < 3; ++z) {
                    for (int x = -3; x < 3; ++x) {
                        start.getWorld().getBlockAt(
                                start.getX() + x,
                                start.getY() + y,
                                start.getZ() + z
                        ).setType(Material.AIR);
                    }
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
        return Material.RED_MUSHROOM;
    }


    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
    }


    /**
     * Returns true if the block is any type of Red Mushroom.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isRedMushroom(@NotNull Block block) {
        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(BlockFace.UP, y);

            if (isMushroomBlock(above)) {
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
    public boolean isMushroomBlock(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.RED_MUSHROOM_BLOCK);
    }


}