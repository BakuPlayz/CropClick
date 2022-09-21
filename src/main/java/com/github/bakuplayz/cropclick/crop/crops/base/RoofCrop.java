package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
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
    public void replant(@NotNull Block block) {}


    /**
     * "Harvest all the crops, stacked on each other, starting from the bottom."
     * <p>
     * Checks wheaten or not the roof crop can be harvested,
     * returning true if it successfully harvested them.
     *
     * @param player The player to add the drops to.
     * @param block  The block that was harvested
     * @param crop   The crop that is being harvested.
     *
     * @return The harvest state.
     */
    public boolean harvestAll(@NotNull Player player, @NotNull Block block, @NotNull Crop crop) {
        boolean wasHarvested = true;

        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            if (!wasHarvested) {
                return false;
            }

            wasHarvested = crop.harvest(player);
        }

        return wasHarvested;
    }


    /**
     * "Harvest all the crops, stacked on each other, starting from the bottom."
     * <p>
     * Checks wheaten or not the roof crop can be harvested,
     * returning true if it successfully harvested them.
     *
     * @param container The container to add the drops to.
     * @param block     The block that was harvested
     * @param crop      The crop that is being harvested.
     *
     * @return The harvest state.
     */
    public boolean harvestAll(@NotNull Container container, @NotNull Block block, @NotNull Crop crop) {
        boolean wasHarvested = true;

        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            if (!wasHarvested) {
                return false;
            }

            wasHarvested = crop.harvest(container);
        }

        return wasHarvested;
    }

}