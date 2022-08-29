package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitScheduler;
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
 * @see BaseCrop
 * @see Crop
 * @since 2.0.0
 */
public final class Chorus extends TallCrop {

    private List<Block> choruses;

    private final CropClick plugin;

    private final BukkitScheduler scheduler = Bukkit.getScheduler();


    public Chorus(@NotNull CropClick plugin) {
        super(plugin.getCropsConfig());
        this.choruses = new ArrayList<>();
        this.plugin = plugin;
    }


    @Override
    public @NotNull String getName() {
        return "chorus";
    }


    @Override
    public int getCurrentAge(@NotNull Block block) {
        choruses = new ArrayList<>();
        return getTallness(block);
    }


    @Override
    public void replant(@NotNull Block block) {
        // It's sorting the list of chorus blocks in reverse order, then setting them to air.
        choruses.stream()
                .sorted((o1, o2) -> -1)
                .forEach(b -> b.setType(Material.AIR));

        choruses = new ArrayList<>();
    }


    /**
     * Get the height of a chorus plant by pushing the block onto a stack, then popping it off and pushing its neighbors
     * onto the stack until the stack is empty.
     *
     * @param block The block that the player clicked, harvested.
     *
     * @return The number of chorus blocks in the tree.
     *
     * @apiNote Written by <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>.
     */
    private int getTallness(@NotNull Block block) {
        Stack<Block> stack = new Stack<>();
        stack.push(block);

        while (stack.size() > 0) {
            Block b = stack.pop();

            if (!isChorus(b) || choruses.contains(b)) {
                continue;
            }

            choruses.add(b);
            stack.push(b.getRelative(BlockFace.UP));
            stack.push(b.getRelative(BlockFace.EAST));
            stack.push(b.getRelative(BlockFace.SOUTH));
            stack.push(b.getRelative(BlockFace.WEST));
            stack.push(b.getRelative(BlockFace.NORTH));
        }

        return choruses.size() + 1;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.CHORUS_FRUIT,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 100)
        );
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