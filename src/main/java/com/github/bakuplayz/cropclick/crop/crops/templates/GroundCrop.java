package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class GroundCrop extends Crop {

    public GroundCrop(CropsConfig cropsConfig) {
        super(cropsConfig);
    }

    @Override
    public int getCurrentAge(final @NotNull Block block) {
        return block.getData();
    }


    @Override
    public void harvest(@NotNull Inventory inventory) {
        if (isEnabled()) {
            for (ItemStack item : getDrops()) {
                inventory.addItem(item);
            }
        }

        if (!hasSeed()) return;
        if (getSeed().isEnabled()) {
            ItemStack[] drops = getDrops().toArray(new ItemStack[0]);//getSeed().getDrops().toArray(new ItemStack[0]);
            inventory.addItem(drops);
        }
    }

}