package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.crop.seeds.ground.PoisonousPotato;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the potato crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Potato extends GroundCrop {

    public Potato(@NotNull CropsConfig config) {
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
        return "potato";
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.POTATO_ITEM,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 4),
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
        return new PoisonousPotato(cropsConfig);
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.POTATO;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.POTATO_ITEM;
    }


}