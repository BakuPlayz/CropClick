package com.github.bakuplayz.cropclick.crop.seeds.templates;

import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface Seed {

    @NotNull String getName();

    Drop getDrop();

    boolean hasDrop();

    void harvest(@NotNull Inventory inventory);

    boolean isEnabled();

}