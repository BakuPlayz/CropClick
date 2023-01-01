package com.github.bakuplayz.cropclick.crop.seeds.base;

import com.github.bakuplayz.cropclick.crop.Drop;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * An interface acting as a base for a seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface BaseSeed {

    /**
     * Gets the name of the seed.
     *
     * @return the name of the seed.
     */
    @NotNull String getName();

    /**
     * Gets the drop of the seed.
     *
     * @return the drop of the seed.
     */
    Drop getDrop();

    /**
     * Checks whether the seed has a drop.
     *
     * @return true if the seed has a drop, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    /**
     * Checks whether the seed can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param inventory The inventory to add the drops to.
     *
     * @return The harvest state.
     */
    boolean harvest(@NotNull Inventory inventory);

    /**
     * Gets the menu type to display of the seed in menu.
     *
     * @return the menu type of the seed.
     */
    @NotNull Material getMenuType();

    /**
     * Checks whether the seed is enabled.
     *
     * @return true if the seed is enabled, otherwise false.
     */
    boolean isEnabled();

}