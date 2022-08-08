package com.github.bakuplayz.cropclick.crop.seeds;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
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
public final class PoisonousPotato extends BaseSeed {

    public PoisonousPotato(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "poisonousPotato";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.POISONOUS_POTATO,
                cropsConfig.getSeedDropName(getName()),
                cropsConfig.getSeedDropAmount(getName(), 1),
                cropsConfig.getSeedDropChance(getName(), 10)
        );
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.POISONOUS_POTATO;
    }

}