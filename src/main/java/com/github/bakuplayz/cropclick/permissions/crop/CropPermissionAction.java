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

import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * An enumeration that acts as a specific action for a crop permission, or all crop permissions.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum CropPermissionAction {

    PLANT,
    HARVEST,
    DESTROY;


    /**
     * Gets the {@link CropPermission permission} type for performing the given action to {@link Crop all crops}.
     *
     * @return the "all permission" for the action, otherwise null.
     */
    @Contract(pure = true)
    public @Nullable Permission getAllPermission() {
        switch (this) {
            case PLANT:
                return CropPermission.PLANT_ALL_CROPS;

            case HARVEST:
                return CropPermission.HARVEST_ALL_CROPS;

            case DESTROY:
                return CropPermission.DESTROY_ALL_CROPS;

            default:
                return null;
        }
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