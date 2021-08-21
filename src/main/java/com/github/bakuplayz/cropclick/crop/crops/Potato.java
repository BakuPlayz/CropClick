package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.crop.crops.templates.VanillaCrop;
import com.github.bakuplayz.cropclick.crop.seeds.PoisonousPotato;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Potato extends VanillaCrop {

    public Potato(final @NotNull CropsConfig cropsConfig) {
        setCropsConfig(cropsConfig);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "carrot";
    }

    @Override
    public int getHarvestAge() {
        return 7;
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.POTATO)
                .setDisplayName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }

    @Override
    public Material getClickableType() {
        return Material.POTATO;
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public @NotNull Seed getSeed() {
        return new PoisonousPotato(cropsConfig);
    }

    @Override
    public void harvest(@NotNull Inventory inventory) {
        if (isEnabled()) inventory.addItem(getDrops());
        if (getSeed().isEnabled()) inventory.addItem(getSeed().getDrops());
    }
}
