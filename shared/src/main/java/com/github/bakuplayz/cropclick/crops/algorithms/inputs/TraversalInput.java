/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.bakuplayz.cropclick.crops.algorithms.inputs;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.github.bakuplayz.cropclick.crops.algorithms.AgeTraversalAlgorithm.Direction;

@Getter
public class TraversalInput {

    private static final List<Direction> DEFAULT_DIRECTIONS = Arrays.asList(
            b -> b.getRelative(BlockFace.UP),
            b -> b.getRelative(BlockFace.EAST),
            b -> b.getRelative(BlockFace.SOUTH),
            b -> b.getRelative(BlockFace.WEST),
            b -> b.getRelative(BlockFace.NORTH)
    );

    private final Block block;

    private final List<Block> resulting;

    private final List<Direction> directions;

    private final Function<Block, Boolean> filter;


    public TraversalInput(@NotNull Block block, @NotNull Function<Block, Boolean> filter, @NotNull List<Block> resulting) {
        this(block, filter, resulting, DEFAULT_DIRECTIONS);
    }


    protected TraversalInput(@NotNull Block block, @NotNull Function<Block, Boolean> filter, @NotNull List<Block> resulting, @NotNull List<Direction> directions) {
        this.block = block;
        this.filter = filter;
        this.resulting = resulting;
        this.directions = directions;
    }

}
