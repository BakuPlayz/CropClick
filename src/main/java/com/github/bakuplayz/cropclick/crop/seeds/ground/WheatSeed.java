package com.github.bakuplayz.cropclick.crop.seeds.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents a wheat seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseSeed
 * @since 2.0.0
 */
public final class WheatSeed extends Seed {

    public WheatSeed(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "wheatSeed";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.WHEAT_SEEDS,
                seedSection.getDropName(getName()),
                seedSection.getDropAmount(getName(), 3),
                seedSection.getDropChance(getName(), 80)
        );
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.WHEAT_SEEDS;
    }

}