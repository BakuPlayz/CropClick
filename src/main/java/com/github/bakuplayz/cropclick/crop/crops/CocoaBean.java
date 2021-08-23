package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.crop.crops.templates.VanillaCrop;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CocoaBean extends VanillaCrop {

    public CocoaBean(final @NotNull CropsConfig cropsConfig) {
        setCropsConfig(cropsConfig);
    }

    @Override
    public String getName() {
        return "cocoaBean";
    }

    @Override
    public int getHarvestAge() {
        return 7;
    }

    @Override
    public @NotNull ItemStack getDrops() {
        return new ItemUtil(Material.COCOA)
                .setDisplayName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }

    @Override
    public Material getClickableType() {
        return Material.COCOA;
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
