package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
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
 * @since 2.0.0
 */
public final class SweetBerries extends GroundCrop {

    public SweetBerries(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "sweetBerries";
    }


    @Override
    public int getHarvestAge() {
        return 3;
    }


    @Contract(" -> new")
    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.SWEET_BERRIES,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 3),
                cropSection.getDropChance(getName(), 80)
        );
    }


    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.SWEET_BERRY_BUSH;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.SWEET_BERRIES;
    }

}