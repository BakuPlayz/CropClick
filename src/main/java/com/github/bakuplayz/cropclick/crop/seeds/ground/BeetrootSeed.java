package com.github.bakuplayz.cropclick.crop.seeds.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents a beetroot seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseSeed
 * @since 2.0.0
 */
public final class BeetrootSeed extends BaseSeed {

    public BeetrootSeed(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Seed seed}.
     *
     * @return the name of the seed.
     */
    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "beetrootSeed";
    }


    /**
     * Gets the {@link Seed seed's} drop.
     *
     * @return the seed's drop.
     */
    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.BEETROOT_SEEDS,
                seedSection.getDropName(getName()),
                seedSection.getDropAmount(getName(), 2),
                seedSection.getDropChance(getName(), 80)
        );
    }


    /**
     * Gets the {@link BaseSeed seed's} menu type.
     *
     * @return the seed's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.BEETROOT_SEEDS;
    }

}