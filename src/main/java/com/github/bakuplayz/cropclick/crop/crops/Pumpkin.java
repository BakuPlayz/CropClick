package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see BaseCrop
 * @since 1.6.0
 */
public final class Pumpkin extends GroundCrop {

    public Pumpkin(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    public @NotNull String getName() {
        return "pumpkin";
    }


    @Override
    public int getHarvestAge() {
        return 0;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.PUMPKIN,
                cropsConfig.getCropDropName(getName()),
                cropsConfig.getCropDropAmount(getName()),
                cropsConfig.getCropDropChance(getName())
        );
    }


    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public void replant(@NotNull Block block) {
        block.setType(Material.AIR);
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.PUMPKIN;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.PUMPKIN;
    }

}