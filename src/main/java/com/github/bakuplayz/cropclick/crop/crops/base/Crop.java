package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
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
public interface Crop {

    @NotNull String getName();

    int getHarvestAge();

    int getCurrentAge(@NotNull Block clickedBlock);

    Drop getDrop();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    boolean dropAtLeastOne();

    Seed getSeed();

    boolean hasSeed();

    void harvest(@NotNull Player player);

    void harvest(@NotNull Container container);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isHarvestAge(@NotNull Block clickedBlock);

    boolean canHarvest(@NotNull Player player);

    boolean isHarvestable();

    void replant(@NotNull Block clickedBlock);

    boolean shouldReplant();

    void playSounds(@NotNull Block block);

    void playParticles(@NotNull Block block);

    @NotNull Material getClickableType();

    @NotNull Material getMenuType();

    boolean isLinkable();

}