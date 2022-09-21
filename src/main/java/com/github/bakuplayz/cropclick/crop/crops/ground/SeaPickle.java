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
     * "Harvesting all the sea pickles."
     * <p>
     * Checks wheaten or not the sea pickles can be harvested,
     * returning true if it successfully harvested them.
     *
     * @param player The player to add the drops to.
     * @param block  The block that was harvested.
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
     * "Harvesting all the sea pickles."
     * <p>
     * Checks wheaten or not the sea pickles can be harvested,
     * returning true if it successfully harvested them.
     *
     * @param container The container to add the drops to.
     * @param block     The block that was harvested.
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