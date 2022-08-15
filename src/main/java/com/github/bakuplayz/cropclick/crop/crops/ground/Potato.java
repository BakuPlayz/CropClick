package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.PoisonousPotato;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Potato extends GroundCrop {

    public Potato(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "potato";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.POTATO,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 4),
                cropSection.getDropChance(getName(), 80)
        );
    }


    @Override
    @Contract(value = " -> new", pure = true)
    public @NotNull Seed getSeed() {
        return new PoisonousPotato(cropsConfig);
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.POTATOES;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.POTATO;
    }


}