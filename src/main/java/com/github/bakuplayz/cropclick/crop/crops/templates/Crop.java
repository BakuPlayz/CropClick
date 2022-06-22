package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Random;

public abstract class Crop {

    private final Random rand = new Random();

    protected final CropsConfig cropsConfig;


    public Crop(final CropsConfig config) {
        this.cropsConfig = config;
    }

    public abstract String getName();

    public abstract int getHarvestAge();

    public abstract int getCurrentAge(final @NotNull Block block);

    public abstract Collection<Drop> getDrops();

    public boolean hasDrops() {
        if (getDrops() == null) return false;
        return !getDrops().isEmpty();
    }

    public boolean willDrop() {
        if (!isEnabled()) return false;
        return getDropChance() >= rand.nextDouble();
    }

    public abstract Seed getSeed();

    public boolean hasSeed() {
        return getSeed() != null;
    }

    public abstract void harvest(final Inventory inventory);

    public boolean isHarvestable(final Block block) {
        if (!isEnabled()) return false;
        if (getDropChance() <= 0) return false;
        return getHarvestAge() != getCurrentAge(block);
    }

    public boolean canHarvest(final @NotNull Player player) {
        return player.hasPermission("cropclick.*")
                || player.hasPermission("cropclick.harvest.*")
                || player.hasPermission("cropclick.harvest." + getName());
    }

    public void replant(final @NotNull Block block) {
        block.setType(Material.AIR);
    }

    public abstract Material getClickableType();

    public boolean isEnabled() {
        return cropsConfig.isCropEnabled(getName());
    }

}