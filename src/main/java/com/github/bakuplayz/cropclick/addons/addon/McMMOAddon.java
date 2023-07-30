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

package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.gmail.nossr50.api.ExperienceAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the <a href="https://www.spigotmc.org/resources/official-mcmmo-original-author-returns.64348/">mcMMO</a> addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class McMMOAddon extends Addon {

    private final AddonConfigSection addonSection;


    public McMMOAddon(@NotNull CropClick plugin) {
        super(plugin, "mcMMO");
        this.addonSection = plugin.getCropsConfig().getAddonSection();
    }


    /**
     * Adds the {@link McMMOAddon mcMMO} experience based on the {@link Crop provided crop} to the {@link Player provided player}.
     *
     * @param player the player to receive the experience.
     * @param crop   the crop to base the experience on.
     */
    public void addExperience(@NotNull Player player, @NotNull Crop crop) {
        ExperienceAPI.addRawXP(player,
                "Herbalism",
                getExperience(crop.getName()),
                "UNKNOWN"
        );
    }


    /**
     * Gets the {@link McMMOAddon mcMMO} experience for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the experience for the crop.
     */
    private int getExperience(@NotNull String cropName) {
        return addonSection.getMcMMOExperience(cropName);
    }

}