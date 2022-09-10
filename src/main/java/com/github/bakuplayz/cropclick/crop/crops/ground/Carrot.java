package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Carrot extends GroundCrop {

    public Carrot(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "carrot";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.CARROT_ITEM,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 4),
                cropSection.getDropChance(getName(), 80)
        );
    }


    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public boolean hasSeed() {
        return false;
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.CARROT;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.CARROT_ITEM;
    }


}