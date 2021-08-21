package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.templates.VanillaCrop;
import com.github.bakuplayz.cropclick.crop.seeds.BeetrootSeed;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Beetroot extends VanillaCrop {

    public Beetroot(final @NotNull CropsConfig cropsConfig) {
        setCropsConfig(cropsConfig);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "beetroot";
    }

    @Override
    public int getHarvestAge() {
        return 7;
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.BEETROOT)
                .setDisplayName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }

    @Override
    public Material getClickableType() {
        return Material.BEETROOT;
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public @NotNull Seed getSeed() {
        return new BeetrootSeed(cropsConfig);
    }

    @Override
    public void harvest(@NotNull Inventory inventory) {
        if (isEnabled()) inventory.addItem(getDrops());
        if (getSeed().isEnabled()) inventory.addItem(getSeed().getDrops());
    }
}
