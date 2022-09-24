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
public interface BaseSeed {

    @NotNull
    String getName();

    Drop getDrop();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    /**
     * Checks wheaten or not the seed can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param inventory The inventory to add the drops to.
     *
     * @return The harvest state.
     */
    boolean harvest(@NotNull Inventory inventory);

    @NotNull
    Material getMenuType();

    boolean isEnabled();

}