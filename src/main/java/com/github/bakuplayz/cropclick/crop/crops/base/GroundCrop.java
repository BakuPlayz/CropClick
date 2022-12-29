package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the base of a ground crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @since 2.0.0
 */
public abstract class GroundCrop extends Crop {

    public GroundCrop(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public int getHarvestAge() {
        return 7;
    }

}