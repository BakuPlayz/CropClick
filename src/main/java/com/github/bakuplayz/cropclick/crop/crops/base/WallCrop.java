package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
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


    /**
     * Returns the crop's seed.
     *
     * @return the crop's seed or null (default: null).
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