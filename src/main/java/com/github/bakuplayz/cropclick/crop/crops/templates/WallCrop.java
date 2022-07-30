package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Crop
 * @since 1.6.0
 */
public abstract class WallCrop extends BaseCrop {

    public int getCurrentAge(@NotNull Block block) {
        return block.getData();
    }


    @Contract(pure = true)
    @Override
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public boolean hasSeed() {
        return false;
    }

}