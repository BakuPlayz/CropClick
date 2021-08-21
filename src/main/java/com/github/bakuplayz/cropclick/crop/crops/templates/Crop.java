package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class Crop {

    protected @Setter CropsConfig cropsConfig;

    public abstract String getName();

    public abstract String getDropName();

    public abstract int getDropAmount();

    public abstract int getHarvestAge();

    public abstract int getCurrentAge(final @NotNull Block block);

    public abstract double getDropChance();

    public double getRandomDropChance() {
        return new Random().nextDouble();
    }

    public abstract ItemStack getDrops();

    public abstract Material getClickableType();

    public abstract Seed getSeed();

    public abstract boolean isEnabled();

    public boolean hasHarvestPermission(final @NotNull Player player) {
        return player.hasPermission("cropclick.*") || player.hasPermission("cropclick.harvest.*") || player.hasPermission("cropclick.harvest." + getName());
    }

    public abstract void harvest(final Inventory inventory);

    public void replant(final @NotNull Block block) {
        block.setType(Material.AIR);
    }
}