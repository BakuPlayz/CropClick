package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class TallCrop extends BaseCrop {

    public TallCrop(@NotNull CropsConfig cropsConfig) {
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
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public boolean hasSeed() {
        return false;
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

}