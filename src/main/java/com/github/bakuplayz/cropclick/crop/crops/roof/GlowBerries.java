package com.github.bakuplayz.cropclick.crop.crops.roof;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.RoofCrop;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A class that represents the glow berries crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class GlowBerries extends RoofCrop {

    private List<Integer> berryYPositions;


    public GlowBerries(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
        this.berryYPositions = new ArrayList<>();
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @Override
    public @NotNull String getName() {
        return "glowBerries";
    }


    /**
     * Gets the harvest age of the {@link Crop crop}.
     *
     * @return the crop's harvest age (default: 1).
     */
    @Override
    public int getHarvestAge() {
        return 1;
    }


    /**
     * Gets the current age of the {@link Crop crop} provided the {@link Block crop block}.
     *
     * @param block the crop block.
     *
     * @return the crop's current age.
     */
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

            if (!isGlowBerriesType(currentBlock)) {
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


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.GLOW_BERRIES,
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


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.CAVE_VINES_PLANT;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.GLOW_BERRIES;
    }


    /**
     * Checks whether the {@link Crop crop} is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link GlowBerries glow berries}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isGlowBerriesType(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.CAVE_VINES, Material.CAVE_VINES_PLANT);
    }

}