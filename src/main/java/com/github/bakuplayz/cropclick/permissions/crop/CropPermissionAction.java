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