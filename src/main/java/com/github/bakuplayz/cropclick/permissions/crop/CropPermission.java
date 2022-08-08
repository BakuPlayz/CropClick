package com.github.bakuplayz.cropclick.permissions.crop;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CropPermission extends Permission {

    /**
     * Permission to harvest all the crops.
     */
    public static Permission HARVEST_ALL_CROPS = new Permission(
            "cropclick.harvest.*",
            "Permission to harvest all crops.",
            PermissionDefault.OP
    );

    /**
     * Permission to plant all the crops.
     */
    public static Permission PLANT_ALL_CROPS = new Permission(
            "cropclick.plant.*",
            "Permission to plant all crops.",
            PermissionDefault.OP
    );

    /**
     * Permission to destroy all the crops.
     */
    public static Permission DESTROY_ALL_CROPS = new Permission(
            "cropclick.destroy.*",
            "Permission to destroy all crops.",
            PermissionDefault.OP
    );


    /**
     * Constructor for creating a crop permission.
     */
    public CropPermission(@NotNull String cropName, @NotNull CropPermissionType type) {
        super("cropclick." + type.getName() + "." + cropName, PermissionDefault.OP);
        setDescription("Permission to " + type.getName() + " the " + cropName + " crop.");
    }


    /**
     * Constructor for creating a crop base permission (i.e. ground, tall or wall).
     */
    public CropPermission(@NotNull CropPermissionBase base, @NotNull CropPermissionType type) {
        super("cropclick." + type.getName() + "." + base.getName(), PermissionDefault.OP);
        setDescription(base.getDescription(type));
    }

}