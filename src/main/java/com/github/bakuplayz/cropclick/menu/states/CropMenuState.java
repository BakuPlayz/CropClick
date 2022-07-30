package com.github.bakuplayz.cropclick.menu.states;

import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public enum CropMenuState {
    NAME,
    JOBS_REBORN,
    MCMMO,
    CROP,
    SOUNDS,
    PARTICLES;


    @NotNull
    public String getType() {
        return name().toLowerCase();
    }

}