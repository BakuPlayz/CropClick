package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the base of a ground crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class GroundCrop extends BaseCrop {

    public GroundCrop(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    /**
     * Gets the harvest age for the crop.
     *
     * @return the harvest age (default: 7).
     */
    @Override
    public int getHarvestAge() {
        return 7;
    }

}