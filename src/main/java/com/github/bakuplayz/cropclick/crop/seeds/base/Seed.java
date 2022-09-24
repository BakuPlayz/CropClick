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
public abstract class Seed implements BaseSeed {

    protected final CropsConfig cropsConfig;


    protected final SeedConfigSection seedSection;


    public Seed(@NotNull CropsConfig config) {
        this.seedSection = config.getSeedSection();
        this.cropsConfig = config;
    }


    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    /**
     * Checks wheaten or not the seed can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param inventory The inventory to add the drops to.
     *
     * @return The harvest state.
     */
    @Override
    public boolean harvest(@NotNull Inventory inventory) {
        if (!hasDrop()) {
            return false;
        }

        Drop drop = getDrop();
        if (!drop.willDrop()) {
            return false;
        }

        ItemStack dropItem = drop.toItemStack(
                hasNameChanged()
        );

        if (dropItem.getAmount() != 0) {
            inventory.addItem(dropItem);
        }

        return true;
    }


    @Override
    public boolean isEnabled() {
        return seedSection.isEnabled(getName());
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}