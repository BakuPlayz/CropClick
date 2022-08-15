package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
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
public final class Cactus extends TallCrop {

    public Cactus(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "cactus";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.CACTUS,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.CACTUS;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.CACTUS;
    }


    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
    }

}