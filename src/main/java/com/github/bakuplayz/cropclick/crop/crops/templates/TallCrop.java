package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class TallCrop extends GroundCrop {

    public TallCrop(CropsConfig cropsConfig) {
        super(cropsConfig);
    }

    @Override
    public int getCurrentAge(final @NotNull Block block) {
        int minHeight = block.getLocation().getBlockY();
        int maxHeight = block.getWorld().getMaxHeight();

        int height = 0;
        for (int y = minHeight; y <= maxHeight; ++y) {
            Block blockAbove = block.getWorld().getBlockAt(block.getX(), y, block.getZ());

            if (BlockUtil.isAir(blockAbove)) break;
            if (!BlockUtil.isSameType(block, blockAbove)) break;

            ++height;
        }

        return height;
    }

    @Override
    public int getHarvestAge() {
        return 2;
    }

}