package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.templates.VanillaGroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class Carrot extends VanillaGroundCrop {

    public Carrot(@NotNull CropsConfig config) {
        super(config);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "carrot";
    }

    @Contract(" -> new")
    @Override
    public @NotNull @Unmodifiable Collection<Drop> getDrops() {
        return Collections.singleton(
                new Drop(Material.CARROT,
                        cropsConfig.getCropDropName(getName()),
                        cropsConfig.getCropDropAmount(getName()),
                        cropsConfig.getCropDropChance(getName())
                )
        );
    }

    @Contract(pure = true)
    @Override
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

}