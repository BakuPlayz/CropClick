package com.github.bakuplayz.cropclick.crop.seeds;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.templates.VanillaSeed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WheatSeed extends VanillaSeed {

    public WheatSeed(final @NotNull CropsConfig config) {
        setCropsConfig(config);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "wheatSeed";
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.SEEDS)
                .setName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }
}