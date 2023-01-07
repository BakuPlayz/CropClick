package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the kelp crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Kelp extends TallCrop {

    public Kelp(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the name of the crop.
     */
    @Override
    public @NotNull String getName() {
        return "kelp";
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
        int minHeight = block.getLocation().getBlockY();
        int maxHeight = block.getWorld().getMaxHeight();

        int height = 0;
        for (int y = minHeight; y <= maxHeight; ++y) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    y,
                    block.getZ()
            );

            if (BlockUtils.isAir(currentBlock)) {
                break;
            }

            if (isKelpType(currentBlock)) {
                ++height;
            }
        }

        return height;
    }


    /**
     * Gets the {@link Crop crop's} drop.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.KELP,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
    }


    /**
     * Replants the {@link Crop crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        int height = getCurrentAge(block) - 1;
        for (int y = height; y > 0; --y) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    block.getY() + y,
                    block.getZ()
            );
            currentBlock.setType(Material.WATER);
        }

        if (!shouldReplant()) {
            block.setType(Material.WATER);
        }
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the clickable type of the crop.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.KELP_PLANT;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the menu type of the crop.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.KELP;
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link Kelp kelp}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isKelpType(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.KELP, Material.KELP_PLANT);
    }

}