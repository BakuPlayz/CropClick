package com.github.bakuplayz.cropclick.crop.seeds.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class BaseSeed implements Seed {

    protected final CropsConfig cropsConfig;


    public BaseSeed(@NotNull CropsConfig config) {
        this.cropsConfig = config;
    }


    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    @Override
    public void harvest(@NotNull Inventory inventory) {
        if (!hasDrop()) return;

        Drop drop = getDrop();
        if (!drop.willDrop()) {
            return;
        }

        ItemStack dropItem = drop.toItemStack(
                hasNameChanged()
        );
        if (dropItem.getAmount() != 0) {
            inventory.addItem(dropItem);
        }
    }


    @Override
    public boolean isEnabled() {
        return cropsConfig.isSeedEnabled(getName());
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}