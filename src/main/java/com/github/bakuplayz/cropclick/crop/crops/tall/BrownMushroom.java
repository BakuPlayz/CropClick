package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseCrop
 * @see Crop
 * @since 2.0.0
 */
public final class BrownMushroom extends TallCrop {

    private List<Block> mushrooms;


    public BrownMushroom(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "brownMushroom";
    }


    //TODO: Check for actual mushrooms, and not a predefined amount.
    @Override
    public int getCurrentAge(@NotNull Block clickedBlock) {
        mushrooms = new ArrayList<>();

        Block topBlock = getTopBlock(clickedBlock);

        for (int x = -4; x < 4; ++x) {
            for (int z = -4; z < 4; ++z) {

                Block mushroom = topBlock.getWorld().getBlockAt(
                        topBlock.getX() + x,
                        topBlock.getY(),
                        topBlock.getZ() + z
                );

                if (isMushroomBlock(mushroom)) {
                    mushrooms.add(mushroom);
                }

            }
        }

        return mushrooms.size() - 1;
    }


    /**
     * Recursively goes up till it finds the top mushroom block.
     *
     * @param block The block to check
     *
     * @return The top block of a mushroom block.
     */
    private Block getTopBlock(@NotNull Block block) {
        if (!isMushroomBlock(block)) {
            mushrooms.add(block);
            return block;
        }

        mushrooms.add(block);

        return getTopBlock(
                block.getRelative(BlockFace.UP, 1)
        );
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.BROWN_MUSHROOM,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 15)
        );
    }


    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


    //TODO: Check for actual mushrooms, and not a predefined amount.
    @Override
    public void replant(@NotNull Block block) {
        // It's sorting the list of chorus blocks in reverse order, then setting them to air.
        mushrooms.stream()
                 .sorted((unused1, unused2) -> -1)
                 .forEach(b -> b.setType(Material.AIR));

        mushrooms = new ArrayList<>();
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.MUSHROOM_STEM;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.BROWN_MUSHROOM;
    }


    /**
     * Returns true if the block is any type of Brown Mushroom.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isBrownMushroom(@NotNull Block block) {
        for (int y = 0; y < 30; ++y) {
            Block above = block.getRelative(BlockFace.UP, y);

            if (isMushroomBlock(above)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns true if the block is any type of Brown Mushroom Block.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    private boolean isMushroomBlock(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.BROWN_MUSHROOM_BLOCK);
    }

}