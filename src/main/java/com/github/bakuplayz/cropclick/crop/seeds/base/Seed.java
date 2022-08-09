package com.github.bakuplayz.cropclick.crop.seeds.base;

import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Seed {

    @NotNull
    String getName();

    Drop getDrop();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    void harvest(@NotNull Inventory inventory);

    @NotNull
    Material getMenuType();

    boolean isEnabled();

}