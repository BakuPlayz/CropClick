package com.github.bakuplayz.cropclick.crop.crops.templates;

import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class VanillaCrop extends Crop {

    @Override
    public int getCurrentAge(final @NotNull Block block) {
        return block.getData();
    }

    @Contract(" -> new")
    @Override
    public @NotNull String getDropName() {
        return cropsConfig.getCropDropName(getName());
    }

    @Override
    public int getDropAmount() {
        return (int) (cropsConfig.getCropDropAmount(getName()) * getRandomDropChance());
    }

    @Override
    public double getDropChance() {
        return cropsConfig.getCropDropChance(getName());
    }

    @Override
    public boolean isEnabled() {
        return cropsConfig.isCropEnabled(getName());
    }
}
