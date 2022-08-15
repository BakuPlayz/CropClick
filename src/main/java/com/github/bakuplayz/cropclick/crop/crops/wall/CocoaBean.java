package com.github.bakuplayz.cropclick.crop.crops.wall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.WallCrop;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @since 2.0.0
 */
public final class CocoaBean extends WallCrop {

    public CocoaBean(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "cocoaBean";
    }


    @Override
    public int getHarvestAge() {
        return 2;
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.COCOA_BEANS,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 3),
                cropSection.getDropChance(getName(), 80)
        );
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.COCOA;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.COCOA_BEANS;
    }


    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
    }

}