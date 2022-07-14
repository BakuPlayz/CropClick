package com.github.bakuplayz.cropclick.crop.seeds.templates;

import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class BaseSeed implements Seed {

    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }

    @Override
    public void harvest(@NotNull Inventory inventory) {
        if (!hasDrop()) return;

        Drop drop = getDrop();
        if (!drop.willDrop()) return;

        inventory.addItem(drop.toItemStack());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
