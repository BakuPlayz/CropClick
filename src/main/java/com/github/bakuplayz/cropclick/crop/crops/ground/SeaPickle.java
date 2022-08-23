package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @see Crop
 * @since 2.0.0
 */
public final class SeaPickle extends GroundCrop {

    public SeaPickle(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "seaPickle";
    }


    @Override
    public int getHarvestAge() {
        return 2;
    }


    @Override
    public int getCurrentAge(@NotNull Block block) {
        return ((org.bukkit.block.data.type.SeaPickle) block.getBlockData()).getPickles();
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.SEA_PICKLE,
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
    public @NotNull Material getClickableType() {
        return Material.SEA_PICKLE;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.SEA_PICKLE;
    }


    @Override
    public void replant(@NotNull Block block) {
        if (!shouldReplant()) {
            block.setType(Material.AIR);
        }

        org.bukkit.block.data.type.SeaPickle seaPickle = (org.bukkit.block.data.type.SeaPickle) block.getBlockData();
        seaPickle.setPickles(1);
        block.setBlockData(seaPickle);
    }


    /**
     * Harvest all the sea pickles on the block.
     *
     * @param player The player who is harvesting the crop
     * @param block  The block that was harvested
     * @param crop   The crop that is being harvested.
     */
    public void harvestAll(@NotNull Player player, @NotNull Block block, @NotNull Crop crop) {
        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            crop.harvest(player);
        }
    }


    /**
     * Harvest all the sea pickles on the block.
     *
     * @param container The container that the crop is in.
     * @param block     The block that is being harvested.
     * @param crop      The crop that is being harvested.
     */
    public void harvestAll(@NotNull Container container, @NotNull Block block, @NotNull Crop crop) {
        int height = getCurrentAge(block);
        for (int i = height; i > 0; --i) {
            crop.harvest(container);
        }
    }

}