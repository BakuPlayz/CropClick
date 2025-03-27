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
package com.github.bakuplayz.cropclick.addons.mcmmo;

import com.github.bakuplayz.cropclick.addons.AddonFunctionality;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.gmail.nossr50.api.ExperienceAPI;
import lombok.AllArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class MCMMOFunctionality implements AddonFunctionality {

    private final CropsConfig config;


    /**
     * Adds the {@link MCMMOAddon mcMMO} experience based on the {@link Crop provided crop} to the {@link Player provided player}.
     *
     * @param player the player to receive the experience.
     * @param crop   the crop to base the experience on.
     */
    public void addExperience(@NotNull OfflinePlayer player, @NotNull Crop crop) {
        ExperienceAPI.addRawXP(player.getPlayer(), "Herbalism",
                config.get(ConfigurationKey.MCMMO_EXPERIENCE, crop.getName()),
                config.get(ConfigurationKey.MCMMO_REASON, crop.getName())
        );
    }

}
