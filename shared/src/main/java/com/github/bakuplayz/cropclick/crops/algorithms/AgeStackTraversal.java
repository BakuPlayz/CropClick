/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2025 BakuPlayz
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
package com.github.bakuplayz.cropclick.crops.algorithms;

import com.github.bakuplayz.cropclick.crops.algorithms.inputs.TraversalInput;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public final class AgeStackTraversal implements AgeTraversalAlgorithm<Integer, TraversalInput> {

    @NotNull
    @Override
    public Integer getCurrentAge(@NotNull TraversalInput input) {
        Set<Block> visited = new HashSet<>();
        Stack<Block> stack = new Stack<>();
        stack.push(input.getBlock());

        while (!stack.isEmpty()) {
            Block current = stack.pop();

            if (!visited.add(current)) continue;
            if (!input.getFilter().apply(current)) continue;

            input.getResulting().add(current);

            for (Direction direction : input.getDirections()) {
                stack.push(direction.move(current));
            }
        }

        return input.getResulting().size() + 1;
    }

}
