package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
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
public final class NetherWart extends GroundCrop {

    public NetherWart(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "netherWart";
    }


    @Override
    public int getHarvestAge() {
        return 3;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.NETHER_STALK,
                cropsConfig.getCropDropName(getName()),
                cropsConfig.getCropDropAmount(getName(), 3),
                cropsConfig.getCropDropChance(getName(), 80)
        );
    }


    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.NETHER_WARTS;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.NETHER_STALK;
    }

}