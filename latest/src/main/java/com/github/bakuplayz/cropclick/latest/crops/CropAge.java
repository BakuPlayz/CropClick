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
package com.github.bakuplayz.cropclick.latest.crops;

import com.github.bakuplayz.cropclick.crops.CropAgeComponent;
import lombok.NoArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.jetbrains.annotations.NotNull;

/**
 * A class implementation for the retrieving the crop's age,
 * imported using reflection during runtime for latest versions (1.13->).
 */
@NoArgsConstructor
public final class CropAge implements CropAgeComponent {

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(@NotNull Block block, int age) {
        Ageable crop = (Ageable) block.getBlockData();
        crop.setAge(age);
        block.setBlockData(crop);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int get(@NotNull Block block) {
        return ((Ageable) block.getBlockData()).getAge();
    }

}
