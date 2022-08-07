package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Crop
 * @since 1.6.0
 */
public abstract class GroundCrop extends BaseCrop {

    public GroundCrop(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public int getHarvestAge() {
        return 7;
    }


    @Override
    @SuppressWarnings("deprecation")
    public int getCurrentAge(@NotNull Block block) {
        return block.getData();
    }


}