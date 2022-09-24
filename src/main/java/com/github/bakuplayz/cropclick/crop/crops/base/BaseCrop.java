package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface BaseCrop {

    @NotNull
    String getName();

    int getHarvestAge();

    int getCurrentAge(@NotNull Block block);

    Drop getDrop();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    boolean dropAtLeastOne();

    BaseSeed getSeed();

    boolean hasSeed();

    /**
     * Checks wheaten or not the crop can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param player The player to add the drops to.
     *
     * @return The harvest state.
     */
    boolean harvest(@NotNull Player player);

    /**
     * Checks wheaten or not the crop can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param container The container to add the drops to.
     *
     * @return The harvest state.
     */
    boolean harvest(@NotNull Container container);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isHarvestAge(@NotNull Block block);

    boolean canHarvest(@NotNull Player player);

    boolean isHarvestable();

    void replant(@NotNull Block block);

    boolean shouldReplant();

    void playSounds(@NotNull Block block);

    void playParticles(@NotNull Block block);

    @NotNull
    Material getClickableType();

    @NotNull
    Material getMenuType();

    boolean isLinkable();

}