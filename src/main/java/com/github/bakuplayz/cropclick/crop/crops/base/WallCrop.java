package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class that represents the base of a wall crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @since 2.0.0
 */
public abstract class WallCrop extends Crop {

    public WallCrop(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public int getHarvestAge() {
        return 7;
    }


    @Override
    @SuppressWarnings("deprecation")
    public int getCurrentAge(@NotNull Block clickedBlock) {
        return clickedBlock.getData();
    }


    @Override
    public void replant(@NotNull Block clickedBlock) {
        if (shouldReplant()) {
            Directional initialDirect = (Directional) clickedBlock.getState().getData();
            BlockFace initialFace = initialDirect.getFacing();

            clickedBlock.setType(getClickableType());

            Directional changedDirect = (Directional) clickedBlock.getState().getData();
            changedDirect.setFacingDirection(initialFace);

            BlockState state = clickedBlock.getState();
            state.setData((MaterialData) changedDirect);
            state.update();
        } else {
            clickedBlock.setType(Material.AIR);
        }
    }


    /**
     * Gets the seed of the wall crop.
     *
     * @return the wall crop's seed or null (default: null).
     */
    @Override
    public @Nullable BaseSeed getSeed() {
        return null;
    }

    /**
     * Checks whether the crop has a seed.
     *
     * @return true if it has a seed, otherwise false (default: false).
     */
    @Override
    public boolean hasSeed() {
        return false;
    }

}