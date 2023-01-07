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

    public TwistingVines(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the name of the crop.
     */
    @Override
    public @NotNull String getName() {
        return "twistingVines";
    }


    /**
     * Gets the {@link Crop crop's} drop.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.TWISTING_VINES,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 80)
        );
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the clickable type of the crop.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.TWISTING_VINES_PLANT;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the menu type of the crop.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.TWISTING_VINES;
    }

}