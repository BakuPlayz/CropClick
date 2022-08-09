package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class WallCrop extends BaseCrop {

    public WallCrop(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public int getHarvestAge() {
        return 7;
    }


    @Override
    @SuppressWarnings("deprecation")
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
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public boolean hasSeed() {
        return false;
    }

}