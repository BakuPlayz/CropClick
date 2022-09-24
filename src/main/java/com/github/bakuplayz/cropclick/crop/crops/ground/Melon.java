package com.github.bakuplayz.cropclick.crop.crops.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Melon extends GroundCrop {

    public Melon(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    public @NotNull String getName() {
        return "melon";
    }


    @Override
    public int getHarvestAge() {
        return 1;
    }


    @Override
    public int getCurrentAge(@NotNull Block block) {
        return 1;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.MELON_SLICE,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 7),
                cropSection.getDropChance(getName(), 80)
        );
    }


    @Override
    @Contract(pure = true)
    public @Nullable BaseSeed getSeed() {
        return null;
    }


    @Override
    public void replant(@NotNull Block block) {
        block.setType(Material.AIR);
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.MELON;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.MELON_SLICE;
    }


    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
    }

}