package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * An interface acting as a base for a crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Crop {

    /**
     * Gets the name of the crop.
     *
     * @return the name of the crop.
     */
    @NotNull String getName();

    /**
     * Gets the harvest age of the crop.
     *
     * @return the harvest age.
     */
    int getHarvestAge();

    /**
     * Gets the current age of the crop.
     *
     * @param block the crop block.
     *
     * @return the current age.
     */
    int getCurrentAge(@NotNull Block block);

    /**
     * Gets the drop of the crop.
     *
     * @return the drop of the crop.
     */
    Drop getDrop();

    /**
     * Checks whether the crop has a drop.
     *
     * @return true if it has a drop, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    /**
     * Checks whether the crop should drop at least one drop.
     *
     * @return true if it should, otherwise false.
     */
    boolean dropAtLeastOne();

    /**
     * Gets the seed if it could be found.
     *
     * @return the seed, or null.
     */
    @Nullable Seed getSeed();

    /**
     * Checks whether a crop has a seed.
     *
     * @return true if it has a seed, otherwise false.
     */
    boolean hasSeed();

    /**
     * Checks whether the crop can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param player The player to add the drops to.
     *
     * @return the harvest state.
     */
    boolean harvest(@NotNull Player player);

    /**
     * Checks whether the crop can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param container The container to add the drops to.
     *
     * @return the harvest state.
     */
    boolean harvest(@NotNull Container container);

    /**
     * Checks whether the crop is its harvest age.
     *
     * @param block the crop block.
     *
     * @return true if it is, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isHarvestAge(@NotNull Block block);

    /**
     * Checks whether the crop can be harvested by the given player.
     *
     * @param player the player to be checked.
     *
     * @return true if the player can harvest, otherwise false.
     */
    boolean canHarvest(@NotNull Player player);

    /**
     * Checks whether the crop is harvestable at all.
     *
     * @return true if it is, otherwise false.
     */
    boolean isHarvestable();

    /**
     * Replants the crop.
     *
     * @param block the crop block.
     */
    void replant(@NotNull Block block);

    /**
     * Checks whether the crop should be replanted.
     *
     * @return true if it should, otherwise false.
     */
    boolean shouldReplant();

    /**
     * Plays the sounds assigned to the crop.
     *
     * @param block the crop block.
     */
    void playSounds(@NotNull Block block);

    /**
     * Plays the effects assigned to the crop.
     *
     * @param block the crop block.
     */
    void playParticles(@NotNull Block block);

    /**
     * Gets the clickable type, meaning the type a {@link Player} or a {@link Autofarm} "clicks" on.
     *
     * @return the clickable type.
     */
    @NotNull Material getClickableType();

    /**
     * Gets the menu type, meaning the type found in menus.
     *
     * @return the menu type.
     */
    @NotNull Material getMenuType();

    /**
     * Checks whether the crop is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false.
     */
    boolean isLinkable();

}