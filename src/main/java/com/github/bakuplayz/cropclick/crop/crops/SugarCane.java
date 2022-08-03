package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.VanillaTallCrop;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see TallCrop
 * @since 1.6.0
 */
public final class SugarCane extends VanillaTallCrop {

    public SugarCane(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "sugarCane";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.SUGAR_CANE,
                cropsConfig.getCropDropName(getName()),
                cropsConfig.getCropDropAmount(getName()),
                cropsConfig.getCropDropChance(getName())
        );
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.SUGAR_CANE_BLOCK;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.SUGAR_CANE;
    }

}