package com.github.bakuplayz.cropclick.crop.seeds.templates;

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

        ItemStack dropItem = drop.toItemStack(hasNameChanged());
        if (dropItem.getAmount() != 0) {
            inventory.addItem(dropItem);
        }
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}