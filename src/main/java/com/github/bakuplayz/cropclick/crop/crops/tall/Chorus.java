package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * A class that represents the chorus crop.
 *
 * @author BakuPlayz, <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @see Crop
 * @see BaseCrop
 * @since 2.0.0
 */
public final class Chorus extends TallCrop {

    private List<Block> choruses;


    public Chorus(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @Override
    public @NotNull String getName() {
        return "chorus";
    }


    /**
     * Gets the current age of the {@link Crop crop} provided the {@link Block crop block}.
     *
     * @param block the crop block.
     *
     * @return the crop's current age.
     */
    @Override
    public int getCurrentAge(@NotNull Block block) {
        choruses = new ArrayList<>();

        Stack<Block> stack = new Stack<>();
        stack.push(block);

        while (stack.size() > 0) {
            Block chorus = stack.pop();

            if (!isChorusType(chorus)) {
                continue;
            }

            if (choruses.contains(chorus)) {
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


    /**
     * Replants the {@link Crop crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        CollectionUtils.reverseOrder(choruses)
                       .forEach(b -> b.setType(Material.AIR));

        choruses = new ArrayList<>();
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Drop getDrop() {
        return new Drop(Material.CHORUS_FRUIT,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 1),
                cropSection.getDropChance(getName(), 80)
        );
    }


    /**
     * Checks whether the {@link Crop crop} should drop at least one drop.
     *
     * @return true if it should, otherwise false (default: false).
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName(), false);
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @Override
    public @NotNull Material getClickableType() {
        return Material.CHORUS_PLANT;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.CHORUS_FRUIT;
    }


    /**
     * Checks whether the {@link Block provided block} is of type {@link Chorus chorus}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isChorusType(@NotNull Block block) {
        return BlockUtils.isAnyType(block, Material.CHORUS_PLANT, Material.CHORUS_FLOWER);
    }

}