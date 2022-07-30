package com.github.bakuplayz.cropclick.crop.seeds.templates;

import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public interface Seed {

    @NotNull String getName();

    Drop getDrop();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    void harvest(@NotNull Inventory inventory);

    @NotNull Material getMenuType();

    boolean isEnabled();

}