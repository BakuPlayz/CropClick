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
package com.github.bakuplayz.cropclick.common;

import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Materials {

    /**
     * Checks whether the {@link Material provided material} matches any of the {@link Material passed materials}.
     *
     * @param block the type to check.
     * @param types the types to match.
     *
     * @return true if it matches any, otherwise false.
     */
    @SuppressWarnings("unused")
    public static boolean isAnyType(@NotNull Material material, @NotNull XMaterial @NotNull ... types) {
        return Arrays.stream(types).anyMatch(type -> type.parseMaterial() == material);
    }

}
