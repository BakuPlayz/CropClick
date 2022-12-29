package com.github.bakuplayz.cropclick.crop.seeds.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.utils.Enableable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents a seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class Seed implements BaseSeed, Enableable {

    protected final CropsConfig cropsConfig;
    protected final SeedConfigSection seedSection;


    public Seed(@NotNull CropsConfig config) {
        this.seedSection = config.getSeedSection();
        this.cropsConfig = config;
    }


    /**
     * Checks whether the seed has a drop.
     *
     * @return true if the seed has a drop, otherwise false.
     */
    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    /**
     * Checks whether the seed can be harvested, returning
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


    /**
     * Checks whether the seed is enabled.
     *
     * @return true if the seed is enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return seedSection.isEnabled(getName());
    }


    /**
     * Checks whether the name has been changed from its default.
     *
     * @return true if its changed, otherwise false.
     */
    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}