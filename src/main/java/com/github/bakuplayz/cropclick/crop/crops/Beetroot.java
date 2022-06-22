package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.templates.Drop;
import com.github.bakuplayz.cropclick.crop.crops.templates.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.BeetrootSeed;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;

public final class Beetroot extends GroundCrop {

    public Beetroot(final @NotNull CropsConfig config) {
        super(config);
    }


    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "beetroot";
    }

    @Override
    public int getHarvestAge() {
        return 7;
    }

    @Contract(" -> new")
    @Override
    public @NotNull @Unmodifiable Collection<Drop> getDrops() {
        return Collections.singleton(
                new Drop(Material.BEETROOT,
                        cropsConfig.getCropDropName(getName()),
                        cropsConfig.getCropDropAmount(getName()),
                        0.05d)
        );
    }

    @Override
    public Material getClickableType() {
        return Material.BEETROOT;
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public @NotNull Seed getSeed() {
        return new BeetrootSeed(cropsConfig);
    }

}