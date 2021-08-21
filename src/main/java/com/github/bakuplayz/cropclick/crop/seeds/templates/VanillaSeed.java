package com.github.bakuplayz.cropclick.crop.seeds.templates;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class VanillaSeed extends Seed {

    @Contract(" -> new")
    @Override
    public @NotNull String getDropName() {
        return cropsConfig.getSeedDropName(getName());
    }

    @Override
    public int getDropAmount() {
        return (int) (cropsConfig.getSeedDropAmount(getName()) * getRandomDropChance());
    }

    @Override
    public double getDropChance() {
        return cropsConfig.getSeedDropChance(getName());
    }

    @Override
    public boolean isEnabled() {
        return cropsConfig.isSeedEnabled(getName());
    }
}
