package com.github.bakuplayz.cropclick.crop.seeds;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.templates.VanillaSeed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class BeetrootSeed extends VanillaSeed {

    public BeetrootSeed(final @NotNull CropsConfig cropsConfig) {
        setCropsConfig(cropsConfig);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "beetrootSeed";
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.BEETROOT_SEEDS)
                .setDisplayName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }
}
