package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.templates.TallCrop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class SugarCane extends TallCrop {

    public SugarCane(final @NotNull CropsConfig config) {
        setConfig(config);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "sugarCane";
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.SUGAR_CANE)
                .setName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }

    @Override
    public Material getClickableType() {
        return Material.SUGAR_CANE;
    }

    @Override
    public Seed getSeed() {
        return null;
    }

}