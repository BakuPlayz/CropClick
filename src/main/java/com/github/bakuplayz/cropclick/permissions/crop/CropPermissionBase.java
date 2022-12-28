package com.github.bakuplayz.cropclick.permissions.crop;

import org.jetbrains.annotations.NotNull;


/**
 * Acts as a specific base for a crop permission for a crop.
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


    public @NotNull String getDescription(@NotNull CropPermissionType type) {
        return "Permission to " + type.getName() + " all the " + getName() + " crops " + example;
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