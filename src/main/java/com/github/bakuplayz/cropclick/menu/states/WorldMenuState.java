package com.github.bakuplayz.cropclick.menu.states;

import com.github.bakuplayz.cropclick.menu.menus.addons.*;
import com.github.bakuplayz.cropclick.menu.menus.main.SettingsMenu;
import com.github.bakuplayz.cropclick.menu.menus.worlds.WorldMenu;


/**
 * An enumeration representing all the states the {@link WorldMenu} could be in.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum WorldMenuState {

    /**
     * The return state to the {@link SettingsMenu}.
     */
    SETTINGS,

    /**
     * The return state to the {@link JobsRebornMenu}.
     */
    JOBS_REBORN,

    /**
     * The return state to the {@link McMMOMenu}.
     */
    MCMMO,

    /**
     * The return state to the {@link OfflineGrowthMenu}.
     */
    OFFLINE_GROWTH,

    /**
     * The return state to the {@link ResidenceMenu}.
     */
    RESIDENCE,

    /**
     * The return state to the {@link TownyMenu}.
     */
    TOWNY,

    /**
     * The return state to the {@link WorldGuardMenu}.
     */
    WORLD_GUARD

}