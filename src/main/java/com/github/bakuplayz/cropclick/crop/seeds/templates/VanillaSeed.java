package com.github.bakuplayz.cropclick.crop.seeds.templates;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;


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


    public VanillaSeed(CropsConfig config) {
        this.cropsConfig = config;
    }


    @Override
    public boolean isEnabled() {
        return cropsConfig.isSeedEnabled(getName());
    }

}