package com.github.bakuplayz.cropclick.crop.crops.tall;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.Drop;
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


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
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
        choruses.sort(
                (c1, c2) -> c2.getY() - c1.getY()
        );

        int i = 0;
        for (Block chorus : choruses) {
            int cooldown = i++ * 20;
            scheduler.runTaskLater(plugin,
                    () -> {
                        chorus.setType(Material.STONE);
                        System.out.println("rann!!!");
                    },
                    cooldown
            );
        }
        choruses = new ArrayList<>();
    }


    private int getTallness(@NotNull Block block) {
        int amount = 2;

        Block above = block.getRelative(BlockFace.UP);
        boolean isAbove = isChorus(above);

        choruses.add(block);
        choruses.add(above);

        int y = 0;
        while (isAbove) {
            ++amount;
            isAbove = isChorus(above);
            above = block.getRelative(BlockFace.UP, ++y);
            choruses.add(above);
        }

        Block west = above.getRelative(BlockFace.WEST, 1);
        Block east = above.getRelative(BlockFace.EAST, 1);
        Block north = above.getRelative(BlockFace.NORTH, 1);
        Block south = above.getRelative(BlockFace.SOUTH, 1);

        boolean isWest = isChorus(west);
        boolean isEast = isChorus(east);
        boolean isNorth = isChorus(north);
        boolean isSouth = isChorus(south);

        if (isWest && isEast && isNorth && isSouth) {
            choruses.add(above.getRelative(BlockFace.WEST));
            choruses.add(above.getRelative(BlockFace.EAST));
            choruses.add(above.getRelative(BlockFace.NORTH));
            choruses.add(above.getRelative(BlockFace.SOUTH));
            return getTallness(west)
                    + getTallness(east)
                    + getTallness(north)
                    + getTallness(south);
        }

        if (isWest && isEast && isNorth) {
            choruses.add(above.getRelative(BlockFace.WEST));
            choruses.add(above.getRelative(BlockFace.EAST));
            choruses.add(above.getRelative(BlockFace.NORTH));
            return getTallness(west) + getTallness(east) + getTallness(north);
        }

        if (isWest && isEast && isSouth) {
            choruses.add(above.getRelative(BlockFace.WEST));
            choruses.add(above.getRelative(BlockFace.EAST));
            choruses.add(above.getRelative(BlockFace.SOUTH));
            return getTallness(west) + getTallness(east) + getTallness(south);
        }

        if (isWest && isEast) {
            choruses.add(above.getRelative(BlockFace.WEST));
            choruses.add(above.getRelative(BlockFace.EAST));
            return getTallness(west) + getTallness(east);
        }

        if (isWest && isNorth) {
            choruses.add(above.getRelative(BlockFace.WEST));
            choruses.add(above.getRelative(BlockFace.NORTH));
            return getTallness(west) + getTallness(north);
        }

        if (isWest && isSouth) {
            choruses.add(above.getRelative(BlockFace.WEST));
            choruses.add(above.getRelative(BlockFace.SOUTH));
            return getTallness(west) + getTallness(south);
        }

        if (isEast && isNorth) {
            choruses.add(above.getRelative(BlockFace.EAST));
            choruses.add(above.getRelative(BlockFace.NORTH));
            return getTallness(east) + getTallness(north);
        }

        if (isEast && isSouth) {
            choruses.add(above.getRelative(BlockFace.EAST));
            choruses.add(above.getRelative(BlockFace.SOUTH));
            return getTallness(east) + getTallness(south);
        }

        if (isNorth && isSouth) {
            choruses.add(above.getRelative(BlockFace.NORTH));
            choruses.add(above.getRelative(BlockFace.SOUTH));
            return getTallness(north) + getTallness(south);
        }

        if (isWest) {
            choruses.add(above.getRelative(BlockFace.WEST));
            return getTallness(west);
        }

        if (isEast) {
            choruses.add(above.getRelative(BlockFace.EAST));
            return getTallness(east);
        }

        if (isNorth) {
            choruses.add(above.getRelative(BlockFace.NORTH));
            return getTallness(north);
        }

        if (isSouth) {
            choruses.add(above.getRelative(BlockFace.SOUTH));
            return getTallness(south);
        }

        return amount;
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


    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName(), false);
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