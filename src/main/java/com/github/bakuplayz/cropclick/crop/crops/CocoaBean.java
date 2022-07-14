package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.templates.VanillaWallCrop;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CocoaBean extends VanillaWallCrop {

    public CocoaBean(@NotNull CropsConfig config) {
        super(config);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "cocoaBean";
    }

    @Contract(" -> new")
    @Override
    public @NotNull @Unmodifiable Collection<Drop> getDrops() {
        return Collections.singleton(
                new Drop(Material.COCOA,
                        cropsConfig.getCropDropName(getName()),
                        cropsConfig.getCropDropAmount(getName()),
                        cropsConfig.getCropDropChance(getName())
                )
        );
    }

    @Override
    public @NotNull Material getClickableType() {
        return Material.COCOA;
    }

}