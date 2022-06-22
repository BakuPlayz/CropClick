package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.templates.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class CocoaBean extends GroundCrop {

    public CocoaBean(final @NotNull CropsConfig config) {
        setConfig(config);
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
                .setName(getDropName())
                .setAmount(getDropAmount())
                .toItemStack();
    }

    @Override
    public Material getClickableType() {
        return Material.COCOA;
    }

    @Override
    public Seed getSeed() {
        return null;
    }

}