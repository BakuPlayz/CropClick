package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.crop.seeds.ground.BeetrootSeed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the beetroot crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Beetroot extends GroundCrop {

    public Beetroot(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "beetroot";
    }


    /**
     * Gets the harvest age of the {@link Crop crop}.
     *
     * @return the crop's harvest age (default: 3).
     */
    @Override
    public int getHarvestAge() {
        return 3;
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.BEETROOT,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 2),
                cropSection.getDropChance(getName(), 80)
        );
    }


    /**
     * Gets the seed of the {@link Crop crop}.
     *
     * @return the crop's seed.
     */
    @Override
    @Contract(value = " -> new", pure = true)
    public @NotNull Seed getSeed() {
        return new BeetrootSeed(cropsConfig);
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.BEETROOT_BLOCK;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.BEETROOT;
    }

}