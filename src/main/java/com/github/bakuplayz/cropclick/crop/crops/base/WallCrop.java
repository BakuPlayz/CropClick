package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class that represents the base of a wall crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class WallCrop extends BaseCrop {

    public WallCrop(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the {@link Seed seed} of the {@link WallCrop extending wall crop}.
     *
     * @return the seed, otherwise null (default: null).
     */
    @Override
    public @Nullable Seed getSeed() {
        return null;
    }


    /**
     * Checks whether the {@link WallCrop extending tall crop} has a {@link Seed seed}.
     *
     * @return true if it has, otherwise false (default: false).
     */
    @Override
    public boolean hasSeed() {
        return false;
    }

}