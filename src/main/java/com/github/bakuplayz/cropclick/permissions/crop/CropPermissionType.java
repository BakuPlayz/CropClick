package com.github.bakuplayz.cropclick.permissions.crop;

import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public enum CropPermissionType {
    PLANT,
    HARVEST,
    DESTROY;


    /**
     * If this is a plant action, return the plant all crops permission, if this is a harvest action, return the harvest
     * all crops permission, if this is a destroy action, return to destroy all crops permission, otherwise return null.
     *
     * @return A permission object.
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
        }
        return null;
    }


    /**
     * Returns the lowercase name of the enum constant.
     *
     * @return The name of the enum in lowercase.
     */
    public @NotNull String getName() {
        return name().toLowerCase();
    }

}