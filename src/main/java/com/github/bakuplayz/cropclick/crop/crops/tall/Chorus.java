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
import java.util.Stack;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz, Hannes Blåman
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Chorus extends TallCrop {

    private List<Block> choruses;


    public Chorus(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public @NotNull String getName() {
        return "chorus";
    }


    /**
     * Get the height of a chorus plant by pushing the block onto a stack, then popping it off and pushing its neighbors
     * onto the stack until the stack is empty.
     *
     * @param clickedBlock The block that the player clicked, harvested.
     *
     * @return The number of chorus blocks in the tree.
     *
     * @apiNote Written by <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>.
     */
    @Override
    public int getCurrentAge(@NotNull Block clickedBlock) {
        choruses = new ArrayList<>();

        Stack<Block> stack = new Stack<>();
        stack.push(clickedBlock);

        while (stack.size() > 0) {
            Block chorus = stack.pop();

            if (!isChorus(chorus) || choruses.contains(chorus)) {
                continue;
            }

            choruses.add(chorus);
            stack.push(chorus.getRelative(BlockFace.UP));
            stack.push(chorus.getRelative(BlockFace.EAST));
            stack.push(chorus.getRelative(BlockFace.SOUTH));
            stack.push(chorus.getRelative(BlockFace.WEST));
            stack.push(chorus.getRelative(BlockFace.NORTH));
        }

        return choruses.size() + 1;
    }


    @Override
    public void replant(@NotNull Block block) {
        // It's sorting the list of chorus blocks in reverse order, then setting them to air.
        choruses.stream()
                .sorted((unused1, unused2) -> -1)
                .forEach(b -> b.setType(Material.AIR));

        choruses = new ArrayList<>();
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.CHORUS_FRUIT,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 80)
        );
    }


    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.CHORUS_PLANT;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.CHORUS_FRUIT;
    }


    /**
     * Returns true if the given block is chorus.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public boolean isChorus(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.CHORUS_PLANT, Material.CHORUS_FLOWER);
    }

}