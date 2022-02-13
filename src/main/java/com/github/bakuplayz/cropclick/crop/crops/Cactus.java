package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.templates.TallCrop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Cactus extends TallCrop {

    public Cactus(final @NotNull CropsConfig config) {
        setCropsConfig(config);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "cactus";
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.CACTUS)
                .setName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }

    @Override
    public Material getClickableType() {
        return Material.CACTUS;
    }

    @Override
    public @Nullable Seed getSeed() {
        return null;
    }

    @Override
    public void harvest(@NotNull Inventory inventory) {
        if (isEnabled()) inventory.addItem(getDrops());
    }
}