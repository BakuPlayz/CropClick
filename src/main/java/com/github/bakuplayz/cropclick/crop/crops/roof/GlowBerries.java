package com.github.bakuplayz.cropclick.crop.crops.roof;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.RoofCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class GlowBerries extends RoofCrop {

    public GlowBerries(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "glowBerries";
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.GLOW_BERRIES,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
    }


    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.CAVE_VINES_PLANT;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.GLOW_BERRIES;
    }


    //TODO: Add the own implementation to only get the glow berries (& also do not remove everything below)
    @Override
    public void harvestAll(@NotNull Player player, @NotNull Block block, @NotNull Crop crop) {
        super.harvestAll(player, block, crop);
    }


    //TODO: Add the own implementation to only get the glow berries (& also do not remove everything below)
    @Override
    public void harvestAll(@NotNull Container container, @NotNull Block block, @NotNull Crop crop) {
        super.harvestAll(container, block, crop);
    }

}