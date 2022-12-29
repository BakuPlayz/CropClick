package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the twisting vines crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class TwistingVines extends TallCrop {

    public TwistingVines(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "twistingVines";
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.TWISTING_VINES,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 80)
        );
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.TWISTING_VINES_PLANT;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.TWISTING_VINES;
    }

}