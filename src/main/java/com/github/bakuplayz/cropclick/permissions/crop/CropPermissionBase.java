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