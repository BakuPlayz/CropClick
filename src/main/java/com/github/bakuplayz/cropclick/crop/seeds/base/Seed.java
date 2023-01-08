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
public interface Seed {

    /**
     * Gets the name of the implementing seed.
     *
     * @return the name of the seed.
     */
    @NotNull String getName();


    /**
     * Gets the {@link Drop drop} of the implementing seed.
     *
     * @return the drop of the seed.
     */
    Drop getDrop();


    /**
     * Checks whether the implementing seed has a drop.
     *
     * @return true if it has, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();


    /**
     * Harvests the implementing seed.
     *
     * @param inventory the inventory to add the drops to.
     *
     * @return true if the seed was harvested, otherwise false.
     */
    boolean harvest(@NotNull Inventory inventory);


    /**
     * Gets the {@link Material menu type} of the implementing seed.
     *
     * @return the seed's menu type.
     */
    @NotNull Material getMenuType();


    /**
     * Checks whether the implementing seed is enabled.
     *
     * @return true if it is enabled, otherwise false.
     */
    boolean isEnabled();

}