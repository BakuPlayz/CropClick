package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.templates.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.WheatSeed;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Wheat extends GroundCrop {

    public Wheat(final @NotNull CropsConfig config) {
        setConfig(config);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "wheat";
    }

    @Override
    public int getHarvestAge() {
        return 7;
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.WHEAT)
                .setName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }

    @Override
    public Material getClickableType() {
        return Material.WHEAT;
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public Seed getSeed() {
        return new WheatSeed(cropsConfig);
    }

}