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

package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * A class acting a communication tunnel (API) for {@link CropClick} and other plugins.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropClickAPI {

    /**
     * Gets the {@link CropManager} which controls all the {@link Crop crops}.
     */
    private final @Getter CropManager cropManager;

    /**
     * Gets the {@link CropsConfig} which is used register {@link Crop crops}.
     */
    private final @Getter CropsConfig cropsConfig;

    /**
     * Gets the {@link AutofarmManager} which controls all the {@link Autofarm autofarms}.
     */
    private final @Getter AutofarmManager autofarmManager;

    /**
     * Gets the {@link UpdateManager} which handles {@link CropClick} updates.
     */
    private final @Getter UpdateManager updateManager;

    /**
     * Gets the {@link WorldManager} which controls all the {@link FarmWorld farm worlds}.
     */
    private final @Getter WorldManager worldManager;

    /**
     * Gets the {@link AddonManager} which controls all the {@link Addon addons}.
     */
    private final @Getter AddonManager addonManager;


    private CropClickAPI(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.cropsConfig = plugin.getCropsConfig();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.updateManager = plugin.getUpdateManager();
        this.autofarmManager = plugin.getAutofarmManager();
    }

}