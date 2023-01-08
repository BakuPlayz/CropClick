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

    public GroundCrop(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the harvest age of the {@link GroundCrop extending ground crop}.
     *
     * @return the crop's harvest age (default: 7).
     */
    @Override
    public int getHarvestAge() {
        return 7;
    }

}