package com.github.bakuplayz.cropclick.crop.crops.roof;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.RoofCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @see Crop
 * @since 2.0.0
 */
public final class GlowBerries extends RoofCrop {

    private List<Integer> berryYPositions;


    public GlowBerries(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
        this.berryYPositions = new ArrayList<>();
    }


    @Override
    public @NotNull String getName() {
        return "glowBerries";
    }


    @Override
    public int getHarvestAge() {
        return 1;
    }


    @Override
    public int getCurrentAge(@NotNull Block block) {
        int minHeight = block.getLocation().getBlockY();
        int maxHeight = block.getWorld().getMaxHeight();

        berryYPositions = new ArrayList<>();

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

            if (!isGlowBerry(currentBlock)) {
                break;
            }

            CaveVinesPlant vines = (CaveVinesPlant) currentBlock.getBlockData();
            if (vines.isBerries()) {
                berryYPositions.add(currentBlock.getY());
                ++height;
            }
        }

        return height;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.GLOW_BERRIES,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
    }


    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public void replant(@NotNull Block block) {
        for (int y : berryYPositions) {
            Block currentBlock = block.getWorld().getBlockAt(
                    block.getX(),
                    y,
                    block.getZ()
            );

            CaveVinesPlant vines = (CaveVinesPlant) currentBlock.getBlockData();
            if (vines.isBerries()) {
                vines.setBerries(false);
                currentBlock.setBlockData(vines);
            }
        }
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.CAVE_VINES_PLANT;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.GLOW_BERRIES;
    }


    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
    }


    /**
     * Returns true if the given block is a glow berry.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isGlowBerry(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.CAVE_VINES, Material.CAVE_VINES_PLANT);
    }

}