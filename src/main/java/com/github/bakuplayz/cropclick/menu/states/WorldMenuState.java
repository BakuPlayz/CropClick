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