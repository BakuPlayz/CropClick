package com.github.bakuplayz.cropclick.crop.seeds.templates;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public abstract class Seed {

    protected @Setter CropsConfig cropsConfig;

    public abstract String getName();

    public abstract String getDropName();

    public abstract int getDropAmount();

    public abstract double getDropChance();

    public double getRandomDropChance() {
        return new Random().nextDouble();
    }

    public abstract ItemStack getDrops();

    public abstract boolean isEnabled();
}
