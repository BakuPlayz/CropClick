package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.utils.PermissionUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class VanillaWallCrop extends WallCrop {

    protected final @Getter CropsConfig cropsConfig;

    public VanillaWallCrop(CropsConfig config) {
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
        return PermissionUtil.canHarvestCrop(player, getName());
    }

    @Override
    public boolean shouldReplant() {
        return cropsConfig.shouldReplantCrop(getName());
    }

    @Override
    public boolean isEnabled() {
        return cropsConfig.isCropEnabled(getName());
    }

}
