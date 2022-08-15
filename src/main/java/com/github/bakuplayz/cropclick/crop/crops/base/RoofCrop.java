package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class RoofCrop extends BaseCrop {

    public RoofCrop(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public int getHarvestAge() {
        return 2;
    }


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

            if (BlockUtils.isSameType(block, currentBlock)) {
                ++height;
            }
        }

        return height;
    }


    @Override
    public void replant(@NotNull Block block) {
        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    block.getY() + i,
                    block.getZ()
            );
            currentBlock.setType(Material.AIR);
        }

        if (!shouldReplant()) {
            block.setType(Material.AIR);
        }
    }


    /**
     * Harvest all the crops, stacked on each other, starting from the bottom.
     *
     * @param player The player who is harvesting the crop
     * @param block  The block that was clicked.
     * @param crop   The crop that is being harvested.
     */
    public void harvestAll(@NotNull Player player, @NotNull Block block, @NotNull Crop crop) {
        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            crop.harvest(player);
        }
    }


    /**
     * Harvest all the crops, stacked on each other, starting from the bottom.
     *
     * @param container The container that the crop is in.
     * @param block     The block that was clicked.
     * @param crop      The crop that is being harvested.
     */
    public void harvestAll(@NotNull Container container, @NotNull Block block, @NotNull Crop crop) {
        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            crop.harvest(container);
        }
    }

}