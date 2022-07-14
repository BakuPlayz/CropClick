package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Crop {

    @NotNull String getName();

    int getHarvestAge();

    int getCurrentAge(@NotNull Block block);

    Collection<Drop> getDrops();

    boolean hasDrops();

    Seed getSeed();

    boolean hasSeed();

    void harvest(@NotNull Player player);

    void harvest(@NotNull Container container);

    boolean isHarvestable(@NotNull Block block);

    boolean canHarvest(@NotNull Player player);

    void replant(@NotNull Block block);

    @NotNull Material getClickableType();

    boolean shouldReplant();

    boolean isEnabled();

}