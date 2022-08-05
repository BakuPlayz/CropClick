package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see BaseCrop
 * @see Crop
 * @since 1.6.0
 */
public abstract class VanillaWallCrop extends WallCrop {

    protected final @Getter CropsConfig cropsConfig;


    public VanillaWallCrop(@NotNull CropsConfig config) {
        this.cropsConfig = config;
    }


    @Override
    public int getHarvestAge() {
        return 7;
    }


    @Override
    public boolean hasSeed() {
        return getSeed() != null;
    }


    @Override
    public boolean canHarvest(@NotNull Player player) {
        return PermissionUtils.canHarvestCrop(player, getName());
    }


    @Override
    public boolean shouldReplant() {
        return cropsConfig.shouldReplantCrop(getName());
    }


    @Override
    public boolean isHarvestable() {
        return cropsConfig.isCropHarvestable(getName());
    }


    @Override
    public boolean isLinkable() {
        return cropsConfig.isCropLinkable(getName());
    }

}