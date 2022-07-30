package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public interface Crop {

    @NotNull String getName();

    int getHarvestAge();

    int getCurrentAge(@NotNull Block block);

    Drop getDrop();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean hasDrop();

    boolean dropAtLeastOne();

    Seed getSeed();

    boolean hasSeed();

    void harvest(@NotNull Player player);

    void harvest(@NotNull Container container);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isHarvestable(@NotNull Block block);

    boolean canHarvest(@NotNull Player player);

    void replant(@NotNull Block block);

    @NotNull Material getClickableType();

    @NotNull Material getMenuType();

    boolean shouldReplant();

    boolean isEnabled();

}