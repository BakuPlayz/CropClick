package com.github.bakuplayz.cropclick.crop.seeds;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.crop.seeds.base.VanillaSeed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Seed
 * @since 1.6.0
 */
public final class BeetrootSeed extends VanillaSeed {

    public BeetrootSeed(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "beetrootSeed";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.BEETROOT_SEEDS,
                cropsConfig.getSeedDropName(getName()),
                cropsConfig.getSeedDropAmount(getName()),
                cropsConfig.getSeedDropChance(getName())
        );
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.BEETROOT_SEEDS;
    }

}