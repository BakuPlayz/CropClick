package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Waterlogged;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Dripleaf extends TallCrop implements Waterlogged {

    public Dripleaf(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "dripleaf";
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

            if (isDripleaf(currentBlock)) {
                ++height;
            }
        }

        return height;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.BIG_DRIPLEAF,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
    }


    @Override
    public void replant(@NotNull Block block) {
        boolean isWaterLogged = false;

        int height = getCurrentAge(block);
        for (int y = height; y > 0; --y) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    block.getY() + y,
                    block.getZ()
            );

            if (!isWaterLogged) {
                isWaterLogged = isWaterLogged(currentBlock);
            }

            currentBlock.setType(
                    isWaterLogged ? Material.WATER : Material.AIR
            );
        }

        if (!shouldReplant()) {
            block.setType(
                    isWaterLogged ? Material.WATER : Material.AIR
            );
            return;
        }

        block.setType(Material.BIG_DRIPLEAF);
        setWaterLogged(block, isWaterLogged);
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.BIG_DRIPLEAF_STEM;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.BIG_DRIPLEAF;
    }


    /**
     * Returns true if the given block is dripleaf.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isDripleaf(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.BIG_DRIPLEAF, Material.BIG_DRIPLEAF_STEM);
    }


    /**
     * Returns true if the block is waterlogged.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isWaterLogged(@NotNull Block block) {
        if (!(block.getBlockData() instanceof org.bukkit.block.data.type.Dripleaf)) {
            return false;
        }
        return ((org.bukkit.block.data.type.Dripleaf) block.getBlockData()).isWaterlogged();
    }


    /**
     * Set the waterlogged state of the block to the provided waterlogged state.
     *
     * @param block       The block you want to set the waterlogged state of.
     * @param waterlogged Whether the dripleaf is waterlogged.
     */
    @Override
    public void setWaterLogged(@NotNull Block block, boolean waterlogged) {
        org.bukkit.block.data.Waterlogged dripleaf = (org.bukkit.block.data.Waterlogged) block.getBlockData();
        dripleaf.setWaterlogged(waterlogged);
        block.setBlockData(dripleaf);
    }

}