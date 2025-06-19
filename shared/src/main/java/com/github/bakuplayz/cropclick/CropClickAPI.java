/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.world.WorldManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * A class acting a communication tunnel (API) between
 * {@link CropClick} and other plugins.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.0.0
 */
@Getter
public final class CropClickAPI {


    @NotNull
    private final CropManager cropManager;

    @NotNull
    private final WorldManager worldManager;

    @NotNull
    private final AddonManager addonManager;

    @NotNull
    private final UpdateManager updateManager;

    @NotNull
    private final AutofarmManager autofarmManager;


    public CropClickAPI() {
        CropClick plugin = CropClick.getInstance();
        this.cropManager = plugin.getCropManager();
        this.addonManager = plugin.getAddonManager();
        this.updateManager = plugin.getUpdateManager();
        this.worldManager = plugin.getWorldManager();
        this.autofarmManager = plugin.getAutofarmManager();
    }

}