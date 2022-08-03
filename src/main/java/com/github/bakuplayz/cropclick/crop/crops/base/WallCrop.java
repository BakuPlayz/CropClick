package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
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


    @Override
    public void replant(@NotNull Block block) {
        if (shouldReplant()) {
            Directional initialDirect = (Directional) block.getState().getData();
            BlockFace initialFace = initialDirect.getFacing();

            block.setType(getClickableType());

            Directional changedDirect = (Directional) block.getState().getData();
            changedDirect.setFacingDirection(initialFace);

            BlockState state = block.getState();
            state.setData((MaterialData) changedDirect);
            state.update();
        } else {
            block.setType(Material.AIR);
        }
    }


    @Override
    @Contract(pure = true)
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public boolean hasSeed() {
        return false;
    }

}