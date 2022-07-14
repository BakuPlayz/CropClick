package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class TallCrop extends BaseCrop {

    @Override
    public int getCurrentAge(final @NotNull Block block) {
        int minHeight = block.getLocation().getBlockY();
        int maxHeight = block.getWorld().getMaxHeight();

        int height = 0;
        for (int y = minHeight; y <= maxHeight; ++y) {
            Block currentBlock = block.getWorld().getBlockAt(block.getX(), y, block.getZ());

            if (BlockUtil.isAir(currentBlock)) break;
            if (!BlockUtil.isSameType(block, currentBlock)) break;

            ++height;
        }

        return height;
    }

    @Override
    public int getHarvestAge() {
        return 2;
    }

    @Override
    public Seed getSeed() {
        return null;
    }

    @Override
    public boolean hasSeed() {
        return false;
    }

}