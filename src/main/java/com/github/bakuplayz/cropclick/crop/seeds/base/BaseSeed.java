package com.github.bakuplayz.cropclick.crop.seeds.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class BaseSeed implements Seed {

    protected final CropsConfig cropsConfig;


    protected final SeedConfigSection seedSection;


    public BaseSeed(@NotNull CropsConfig config) {
        this.seedSection = config.getSeedSection();
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
        return seedSection.isEnabled(getName());
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}