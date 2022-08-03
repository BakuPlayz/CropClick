package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.VanillaGroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.BeetrootSeed;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see GroundCrop
 * @since 1.6.0
 */
public final class Beetroot extends VanillaGroundCrop {

    public Beetroot(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "beetroot";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.BEETROOT,
                cropsConfig.getCropDropName(getName()),
                cropsConfig.getCropDropAmount(getName()),
                cropsConfig.getCropDropChance(getName())
        );
    }


    @Override
    @Contract(value = " -> new", pure = true)
    public @NotNull Seed getSeed() {
        return new BeetrootSeed(cropsConfig);
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.BEETROOT;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.BEETROOT_BLOCK;
    }

}