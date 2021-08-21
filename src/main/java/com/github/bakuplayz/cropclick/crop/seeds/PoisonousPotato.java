package com.github.bakuplayz.cropclick.crop.seeds;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.templates.VanillaSeed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class PoisonousPotato extends VanillaSeed {

    public PoisonousPotato(final @NotNull CropsConfig cropsConfig) {
        setCropsConfig(cropsConfig);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "poisonousPotato";
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.POISONOUS_POTATO)
                .setDisplayName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }
}
