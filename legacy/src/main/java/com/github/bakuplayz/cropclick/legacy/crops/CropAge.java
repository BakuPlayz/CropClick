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
package com.github.bakuplayz.cropclick.legacy.crops;

import com.github.bakuplayz.cropclick.crops.CropAgeComponent;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

/**
 * A class implementation for the retrieving the crop's age,
 * imported using reflection during runtime for legacy versions (1.8-1.12.x).
 */
@NoArgsConstructor
public final class CropAge implements CropAgeComponent {

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(@NotNull Block block, int age) {
        BlockState state = block.getState();

        if (state.getData() instanceof Directional) {
            Directional initialDirect = (Directional) block.getState().getData();
            BlockFace initialFace = initialDirect.getFacing();

            Directional changedDirect = (Directional) block.getState().getData();
            changedDirect.setFacingDirection(initialFace);

            state.setData((MaterialData) changedDirect);
        }

        state.getData().setData((byte) age);
        state.update();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int get(@NotNull Block block) {
        if (block.getType() == Material.COCOA) {
            CocoaPlant cocoa = (CocoaPlant) block.getState().getData();
            return cocoa.getSize().ordinal();
        }

        return block.getState().getData().getData();
    }

}
