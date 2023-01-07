package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.runnables.particles.Particle;
import com.github.bakuplayz.cropclick.runnables.sounds.Sound;
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
     * Gets the name of the implementing crop.
     *
     * @return the name of the crop.
     */
    @NotNull String getName();

    /**
     * Gets the harvest age of the implementing crop.
     *
     * @return the harvest age.
     */
    int getHarvestAge();

    /**
     * Gets the current age of the implementing crop.
     *
     * @param block the crop block.
     *
     * @return the current age.
     */
    int getCurrentAge(@NotNull Block block);

    /**
     * Gets the drop of the implementing crop.
     *
     * @return the drop of the crop.
     */
    Drop getDrop();

    /**
     * Checks whether the implementing crop has a drop.
     *
     * @return true if it has, otherwise false.
     */
    boolean hasDrop();

    /**
     * Checks whether the implementing crop should drop at least one drop.
     *
     * @return true if it should, otherwise false.
     */
    boolean dropAtLeastOne();

    /**
     * Gets the implementing seed.
     *
     * @return the found seed, otherwise null.
     */
    @Nullable Seed getSeed();

    /**
     * Checks whether the implementing crop has a seed.
     *
     * @return true if it has, otherwise false.
     */
    boolean hasSeed();

    /**
     * Harvests the implementing crop.
     *
     * @param player the player to add the drops to.
     *
     * @return true if harvested, otherwise false.
     */
    boolean harvest(@NotNull Player player);

    /**
     * Harvests the implementing crop.
     *
     * @param container the container to add the drops to.
     *
     * @return true if harvested, otherwise false.
     */
    boolean harvest(@NotNull Container container);

    /**
     * Checks whether the implementing crop is at its harvest age.
     *
     * @param block the crop block.
     *
     * @return true if it is, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isHarvestAge(@NotNull Block block);

    /**
     * Checks whether the {@link Player provided player} can harvest the implementing crop.
     *
     * @param player the player to be checked.
     *
     * @return true if it can, otherwise false.
     */
    boolean canHarvest(@NotNull Player player);

    /**
     * Checks whether the implementing crop is harvestable at all.
     *
     * @return true if it is, otherwise false.
     */
    boolean isHarvestable();

    /**
     * Replants the implementing crop.
     *
     * @param block the crop block to replant.
     */
    void replant(@NotNull Block block);

    /**
     * Checks whether the implementing crop should be replanted.
     *
     * @return true if it should, otherwise false.
     */
    boolean shouldReplant();

    /**
     * Plays the {@link Sound sounds} assigned to the implementing crop.
     *
     * @param block the crop block.
     */
    void playSounds(@NotNull Block block);

    /**
     * Plays the {@link Particle particles} assigned to the implementing crop.
     *
     * @param block the crop block.
     */
    void playParticles(@NotNull Block block);

    /**
     * Gets implementing crop's {@link Material clickable type}.
     *
     * @return the crop's clickable type.
     */
    @NotNull Material getClickableType();

    /**
     * Gets implementing crop's {@link Material menu type}.
     *
     * @return the crop's menu type.
     */
    @NotNull Material getMenuType();

    /**
     * Checks whether the implementing crop is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false.
     */
    boolean isLinkable();

}