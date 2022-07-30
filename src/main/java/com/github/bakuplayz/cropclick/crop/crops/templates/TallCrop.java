package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Crop
 * @since 1.6.0
 */
public abstract class TallCrop extends BaseCrop {

    @Override
    public int getCurrentAge(@NotNull Block block) {
        int minHeight = block.getLocation().getBlockY();
        int maxHeight = block.getWorld().getMaxHeight();

        int height = 0;
        for (int y = minHeight; y <= maxHeight; ++y) {
            Block currentBlock = block.getWorld().getBlockAt(block.getX(), y, block.getZ());

            if (BlockUtil.isAir(currentBlock)) {
                break;
            }

            if (BlockUtil.isSameType(block, currentBlock)) {
                ++height;
            }
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


    @Override
    public void replant(@NotNull Block block) {
        if (!shouldReplant()) return;

        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    block.getY() + i,
                    block.getZ()
            );
            currentBlock.setType(Material.AIR);
        }
    }

}