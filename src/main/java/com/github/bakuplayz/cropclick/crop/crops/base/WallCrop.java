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
 * A class that represents the base of a wall crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class WallCrop extends BaseCrop {

    public WallCrop(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the {@link Seed seed} of the {@link WallCrop extending wall crop}.
     *
     * @return the seed, otherwise null (default: null).
     */
    @Override
    public @Nullable Seed getSeed() {
        return null;
    }


    /**
     * Checks whether the {@link WallCrop extending wall crop} has a {@link Seed seed}.
     *
     * @return true if it has, otherwise false (default: false).
     */
    @Override
    public boolean hasSeed() {
        return false;
    }


    /**
     * Replants the {@link WallCrop extending wall crop}.
     *
     * @param block the crop block to replant.
     */
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

}