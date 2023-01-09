/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
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

package com.github.bakuplayz.cropclick.permissions.crop;

import org.jetbrains.annotations.NotNull;


/**
 * An enumeration that acts as a base for a specific crop permission for a crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum CropPermissionBase {

    GROUND("(e.g. wheat)."),
    TALL("(e.g. cactus)."),
    WALL("(e.g. cocoa bean).");


    private final String example;


    CropPermissionBase(@NotNull String example) {
        this.example = example;
    }


    /**
     * Gets the description of the {@link CropPermissionAction crop action permission}.
     *
     * @param action the action type.
     *
     * @return the description of the {@link CropPermission}.
     */
    public @NotNull String getDescription(@NotNull CropPermissionAction action) {
        return "Permission to " + action.getName() + " all the " + getName() + " crops " + example;
    }


    /**
     * Gets the name of the permission type as all lowercase.
     *
     * @return the name of the permission type.
     */
    public @NotNull String getName() {
        return name().toLowerCase();
    }

}