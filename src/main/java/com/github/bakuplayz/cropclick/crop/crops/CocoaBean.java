package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.VanillaWallCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.WallCrop;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see WallCrop
 * @since 1.6.0
 */
public final class CocoaBean extends VanillaWallCrop {

    public CocoaBean(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "cocoaBean";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.INK_SACK,
                cropsConfig.getCropDropName(getName()),
                cropsConfig.getCropDropAmount(getName()),
                cropsConfig.getCropDropChance(getName()),
                (short) 3
        );
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.COCOA;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.INK_SACK;
    }

}