package com.github.bakuplayz.cropclick.crop.seeds.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Seed
 * @since 1.6.0
 */
public abstract class VanillaSeed extends BaseSeed {

    protected final CropsConfig cropsConfig;


    public VanillaSeed(@NotNull CropsConfig config) {
        this.cropsConfig = config;
    }


    @Override
    public boolean isEnabled() {
        return cropsConfig.isSeedEnabled(getName());
    }

}