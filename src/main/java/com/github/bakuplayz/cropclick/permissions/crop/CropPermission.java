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

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing a crop permission.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropPermission extends Permission {

    /**
     * Permission to harvest all the crops.
     */
    public static final Permission HARVEST_ALL_CROPS = new Permission(
            "cropclick.harvest.*",
            "Permission to harvest all crops.",
            PermissionDefault.OP
    );

    /**
     * Permission to plant all the crops.
     */
    public static final Permission PLANT_ALL_CROPS = new Permission(
            "cropclick.plant.*",
            "Permission to plant all crops.",
            PermissionDefault.OP
    );

    /**
     * Permission to destroy all the crops.
     */
    public static final Permission DESTROY_ALL_CROPS = new Permission(
            "cropclick.destroy.*",
            "Permission to destroy all crops.",
            PermissionDefault.OP
    );


    /**
     * Constructor for creating a crop permission.
     */
    public CropPermission(@NotNull String cropName, @NotNull CropPermissionAction action) {
        super("cropclick." + action.getName() + "." + cropName, PermissionDefault.OP);
        setDescription("Permission to " + action.getName() + " the " + cropName + " crop.");
    }


    /**
     * Constructor for creating a crop base permission (i.e. ground, tall or wall).
     */
    public CropPermission(@NotNull CropPermissionBase base, @NotNull CropPermissionAction action) {
        super("cropclick." + action.getName() + "." + base.getName(), PermissionDefault.OP);
        setDescription(base.getDescription(action));
    }

}