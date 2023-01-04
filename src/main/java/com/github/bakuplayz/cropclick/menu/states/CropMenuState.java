package com.github.bakuplayz.cropclick.menu.states;

import com.github.bakuplayz.cropclick.menu.menus.addons.JobsRebornMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.McMMOMenu;
import com.github.bakuplayz.cropclick.menu.menus.crops.CropMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.NameMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ParticlesMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.SoundsMenu;


/**
 * An enumeration representing all the states the {@link CropMenu} could be in.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum CropMenuState {

    /**
     * The return state to the {@link NameMenu}.
     */
    NAME,

    /**
     * The return state to the {@link JobsRebornMenu}.
     */
    JOBS_REBORN,

    /**
     * The return state to the {@link McMMOMenu}.
     */
    MCMMO,

    /**
     * The return state to the {@link CropMenu}.
     */
    CROP,

    /**
     * The return state to the {@link SoundsMenu}.
     */
    SOUNDS,

    /**
     * The return state to the {@link ParticlesMenu}.
     */
    PARTICLES

}